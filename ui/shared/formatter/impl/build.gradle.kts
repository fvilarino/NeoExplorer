plugins {
  id("neoexplorer.kotlin.library")
  id("neoexplorer.dependency.injection")
}

dependencies {
  implementation(projects.ui.shared.formatter)

  testImplementation(libs.junit.junit)
}
