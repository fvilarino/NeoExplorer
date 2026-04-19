package com.francesc.neoexplorer.data.neo.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val NEO_WS_BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

@ContributesTo(AppScope::class)
interface NeoNetworkModule {
    companion object {
        @Provides
        @Named("nasa_api_key")
        fun provideNasaApiKey(): String = BuildConfig.NASA_API_KEY

        @Provides
        fun provideNeoWsJson(): Json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        @Provides
        fun provideNeoWsOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) {
                            HttpLoggingInterceptor.Level.BODY
                        } else {
                            HttpLoggingInterceptor.Level.NONE
                        }
                    }
                )
                .build()

        @Provides
        @NeoWsRetrofit
        fun provideNeoWsRetrofit(
            okHttpClient: OkHttpClient,
            json: Json,
        ): Retrofit = Retrofit.Builder()
            .baseUrl(NEO_WS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}
