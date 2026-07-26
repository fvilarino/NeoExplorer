plugins {
  id("neoexplorer.android.feature")
  id("neoexplorer.android.library.compose")
  id("neoexplorer.android.library.test")
  id("neoexplorer.dependency.injection")
}

android {
  namespace = "com.francesc.neoexplorer.ui.feature.dashboard"

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

ksp { arg("circuit.codegen.mode", "metro") }

dependencies {
  implementation(projects.core.clock)
  implementation(projects.data.neo)
  implementation(projects.ui.feature.details)
  implementation(projects.ui.shared.asteroid)
  implementation(projects.ui.shared.compose)
  implementation(projects.ui.shared.errormessage)
  implementation(projects.ui.shared.formatter)

  testImplementation(libs.robolectric)
  testImplementation(libs.androidx.compose.ui.ui.test.junit4)
}
