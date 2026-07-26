plugins {
  id("neoexplorer.android.feature")
  id("neoexplorer.android.library.compose")
  id("neoexplorer.dependency.injection")
}

android {
  namespace = "com.francesc.neoexplorer.ui.feature.settings"

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

ksp { arg("circuit.codegen.mode", "metro") }

dependencies {
  implementation(projects.data.preferences)
  implementation(projects.ui.shared.compose)

  testImplementation(libs.robolectric)
  testImplementation(libs.androidx.compose.ui.ui.test.junit4)
}
