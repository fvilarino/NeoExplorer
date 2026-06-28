plugins {
  id("neoexplorer.android.feature")
  id("neoexplorer.android.library.compose")
  id("neoexplorer.dependency.injection")
}

android {
  namespace = "com.francesc.neoexplorer.ui.feature.browse"
}

ksp { arg("circuit.codegen.mode", "metro") }

dependencies {
  implementation(libs.androidx.paging.compose)
  implementation(projects.core.dispatcher)
  implementation(projects.core.formatter)
  implementation(projects.data.neo)
  implementation(projects.ui.feature.details)
  implementation(projects.ui.shared.compose)
  implementation(projects.ui.shared.navigation)
}
