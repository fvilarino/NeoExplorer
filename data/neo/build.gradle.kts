plugins {
    id("neoexplorer.android.library")
}
android {
    namespace = "com.francesc.neoexplorer.data.neo"
}
dependencies {
    api(libs.androidx.paging.common)
    api(libs.org.jetbrains.kotlinx.kotlinx.datetime)
}
