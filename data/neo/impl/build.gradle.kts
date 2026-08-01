import com.android.build.api.variant.BuildConfigField

plugins {
  id("neoexplorer.android.library")
  id("neoexplorer.android.library.test")
  id("neoexplorer.dependency.injection")
  id("neoexplorer.keys.loader")
  alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

val nasaApiKeyProperty = extensions.getByType<NasaConfigKeys>().nasaApiKey

android {
  namespace = "com.francesc.neoexplorer.data.neo.impl"
  buildFeatures {
    buildConfig = true
  }
}

androidComponents {
  onVariants { variant ->
    variant.buildConfigFields?.put(
      "NASA_API_KEY",
      nasaApiKeyProperty.map { key ->
        val escaped = escapeKey(key)
        BuildConfigField("String", "\"$escaped\"", "NASA API Key")
      },
    )
  }
}

private fun escapeKey(key: String): String =
  key.replace("\\", "\\\\").replace("\"", "\\\"").replace("$", "\\$")

dependencies {
  implementation(libs.androidx.collection)
  implementation(libs.androidx.paging.common)
  implementation(libs.com.squareup.okhttp3.okhttp)
  implementation(libs.com.squareup.okhttp3.logging.interceptor)
  implementation(libs.com.squareup.retrofit2.retrofit)
  implementation(libs.com.squareup.retrofit2.converter.kotlinx.serialization)
  implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
  implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
  implementation(projects.data.neo)

  testImplementation(libs.androidx.paging.testing)
}
