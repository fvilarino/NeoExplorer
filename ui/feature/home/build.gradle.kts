plugins {
    id("neoexplorer.android.feature")
    id("neoexplorer.android.library.compose")
    id("neoexplorer.dependency.injection")
}

android {
    namespace = "com.francesc.neoexplorer.ui.feature.home"
}

dependencies {
    implementation(libs.androidx.activity.activity.compose)
    implementation(projects.ui.feature.dashboard)
}
