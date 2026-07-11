plugins {
  id("neoexplorer.android.library")
  id("neoexplorer.android.library.test")
}

android {
  namespace = "com.francesc.neoexplorer.ui.shared.errormessage"
  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  testImplementation(libs.robolectric)
  testImplementation(libs.androidx.test.core)
}
