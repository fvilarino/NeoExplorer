plugins {
    id("neoexplorer.android.feature")
    id("neoexplorer.android.library.compose")
    id("neoexplorer.dependency.injection")
}

android {
    namespace = "com.francesc.neoexplorer.ui.feature.settings"
}

ksp { arg("circuit.codegen.mode", "metro") }

dependencies {
    implementation(projects.data.preferences)
    implementation(projects.ui.shared.compose)
}
