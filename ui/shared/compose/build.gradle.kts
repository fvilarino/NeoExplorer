plugins {
  id("neoexplorer.android.library")
  id("neoexplorer.android.library.compose")
}

android {
  namespace = "com.francesc.neoexplorer.ui.shared.compose"
}

dependencies {
  implementation(projects.ui.shared.styles)
}
