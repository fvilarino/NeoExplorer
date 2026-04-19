plugins {
    id("neoexplorer.android.feature")
    id("neoexplorer.android.library.compose")
    id("neoexplorer.dependency.injection")
}

android {
    namespace = "com.francesc.neoexplorer.ui.feature.dashboard"
}

ksp { arg("circuit.codegen.mode", "metro") }

dependencies {
    implementation(projects.core.clock)
    implementation(projects.core.formatter)
    implementation(projects.data.neo)
    implementation(projects.ui.shared.compose)
}
