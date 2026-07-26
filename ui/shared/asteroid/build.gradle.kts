plugins {
  id("neoexplorer.android.library")
  id("neoexplorer.android.library.compose")
  id("neoexplorer.dependency.injection")
}

android {
  namespace = "com.francesc.neoexplorer.ui.shared.asteroid"
}

dependencies {
  implementation(projects.data.neo)
  implementation(projects.ui.shared.compose)
  implementation(projects.ui.shared.formatter)
  implementation(projects.ui.shared.styles)
}
