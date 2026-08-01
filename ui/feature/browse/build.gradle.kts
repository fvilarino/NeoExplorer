plugins {
  id("neoexplorer.android.feature")
  id("neoexplorer.android.library.compose")
  id("neoexplorer.android.library.test")
  id("neoexplorer.dependency.injection")
}

android {
  namespace = "com.francesc.neoexplorer.ui.feature.browse"

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

ksp { arg("circuit.codegen.mode", "metro") }

dependencies {
  implementation(libs.androidx.paging.compose)
  implementation(projects.core.dispatcher)
  implementation(projects.data.neo)
  implementation(projects.ui.feature.details)
  implementation(projects.ui.shared.asteroid)
  implementation(projects.ui.shared.compose)
  implementation(projects.ui.shared.errormessage)

  testImplementation(libs.androidx.compose.ui.ui.test.junit4)
  testImplementation(libs.androidx.paging.testing)
  testImplementation(libs.org.robolectric.robolectric)
}
