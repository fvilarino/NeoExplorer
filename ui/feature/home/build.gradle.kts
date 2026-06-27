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
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    implementation(projects.ui.feature.dashboard)
    implementation(projects.ui.feature.details)
    implementation(projects.ui.feature.settings)
    implementation(projects.ui.feature.temporalexplorer)
    implementation(projects.ui.shared.navigation)
}
