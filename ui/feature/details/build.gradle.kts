plugins {
  id("neoexplorer.android.feature")
  id("neoexplorer.android.library.compose")
  id("neoexplorer.dependency.injection")
}

android {
  namespace = "com.francesc.neoexplorer.ui.feature.details"
}

ksp { arg("circuit.codegen.mode", "metro") }

dependencies {
  implementation(projects.core.formatter)
  implementation(projects.data.neo)
  implementation(projects.ui.shared.compose)
  implementation(projects.ui.shared.errormessage)
}
