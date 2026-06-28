plugins {
  id("neoexplorer.android.library")
  id("neoexplorer.android.library.compose")
}

android {
  namespace = "com.francesc.neoexplorer.ui.shared.compose"
}

dependencies {
  api(libs.androidx.compose.material3.windowsize)
  implementation(projects.ui.shared.styles)
}
