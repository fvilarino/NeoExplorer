package com.francesc.neoexplorer.data.neo.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val NEO_WS_BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

// Explicit timeouts — OkHttp's built-in defaults are also 10 s, but we pin them
// here so any future OkHttp upgrade cannot silently change behaviour.
private const val CONNECT_TIMEOUT_S = 15L
private const val READ_TIMEOUT_S = 15L
private const val WRITE_TIMEOUT_S = 15L

// Retry configuration for transient server errors.
// NeoWs is GET-only, so unconditional retries are safe.
private const val MAX_RETRIES = 2
private const val DEFAULT_RETRY_DELAY_MS = 1_000L

/**
 * Appends the NASA API key as an `api_key` query parameter to every outgoing request.
 *
 * Registered as a **network** interceptor so it runs *after* the logging interceptor; logged URLs
 * therefore never contain the key.
 */
private class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val url = chain.request().url.newBuilder().addQueryParameter("api_key", apiKey).build()
    return chain.proceed(chain.request().newBuilder().url(url).build())
  }
}

/**
 * Retries requests that fail with HTTP 429 (rate-limited) or 5xx (server error).
 *
 * On a 429 response the `Retry-After` header (delta-seconds integer) is honoured when present;
 * otherwise [DEFAULT_RETRY_DELAY_MS] is used. For 5xx a fixed [DEFAULT_RETRY_DELAY_MS] delay is
 * applied before each attempt.
 *
 * Note: retries are only safe here because all NeoWs endpoints are idempotent GET requests.
 */
private object RetryInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    var response = chain.proceed(request)
    var attempt = 0

    while (attempt < MAX_RETRIES && (response.code == 429 || response.code in 500..599)) {
      val delayMs =
        when {
          response.code == 429 -> {
            response.header("Retry-After")?.toLongOrNull()?.let { it * 1_000L }
              ?: DEFAULT_RETRY_DELAY_MS
          }
          else -> DEFAULT_RETRY_DELAY_MS
        }

      // Must close the previous body before re-issuing the request.
      response.close()
      Thread.sleep(delayMs)

      attempt++
      response = chain.proceed(request)
    }

    return response
  }
}

@ContributesTo(AppScope::class)
interface NeoNetworkModule {
  companion object {
    @Provides @Named("nasa_api_key") fun provideNasaApiKey(): String = BuildConfig.NASA_API_KEY

    @Provides
    fun provideNeoWsJson(): Json = Json {
      ignoreUnknownKeys = true
      coerceInputValues = true
    }

    @Provides
    fun provideNeoWsOkHttpClient(@Named("nasa_api_key") apiKey: String): OkHttpClient =
      OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_S, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_S, TimeUnit.SECONDS)
        // RetryInterceptor runs first so retried requests are also logged below.
        .addInterceptor(RetryInterceptor)
        .addInterceptor(
          HttpLoggingInterceptor().apply {
            level =
              if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
              } else {
                HttpLoggingInterceptor.Level.NONE
              }
          }
        )
        // ApiKeyInterceptor is a *network* interceptor: it runs after the logging interceptor,
        // so the api_key query parameter is never written to Logcat.
        .addNetworkInterceptor(ApiKeyInterceptor(apiKey))
        .build()

    @Provides
    @NeoWsRetrofit
    fun provideNeoWsRetrofit(
      okHttpClient: OkHttpClient,
      json: Json,
    ): Retrofit =
      Retrofit.Builder()
        .baseUrl(NEO_WS_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
  }
}
