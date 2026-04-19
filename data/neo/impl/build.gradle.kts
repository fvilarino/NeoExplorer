plugins {
    id("neoexplorer.android.library")
    id("neoexplorer.android.library.test")
    id("neoexplorer.dependency.injection")
    id("neoexplorer.keys.loader")
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}
val nasaApiKey: String = extensions.getByType<NasaConfigKeys>().nasaApiKey
android {
    namespace = "com.francesc.neoexplorer.data.neo.impl"
    defaultConfig {
        buildConfigField("String", "NASA_API_KEY", "\"$nasaApiKey\"")
    }
    buildFeatures {
        buildConfig = true
    }
}
dependencies {
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
