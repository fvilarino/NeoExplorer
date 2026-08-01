plugins {
  id("neoexplorer.android.library")
  id("neoexplorer.android.library.compose")
  id("neoexplorer.android.library.test")
  id("neoexplorer.dependency.injection")
}

android {
  namespace = "com.francesc.neoexplorer.ui.shared.asteroid"

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  implementation(projects.data.neo)
  implementation(projects.ui.shared.compose)
  implementation(projects.ui.shared.formatter)
  implementation(projects.ui.shared.styles)

  testImplementation(libs.androidx.compose.ui.ui.test.junit4)
  testImplementation(libs.org.robolectric.robolectric)
}
