plugins {
  id("neoexplorer.android.library")
  id("neoexplorer.android.library.test")
  id("neoexplorer.dependency.injection")
  alias(libs.plugins.com.google.protobuf)
}

android {
  namespace = "com.francesc.neoexplorer.data.preferences.impl"
}

protobuf {
  protoc {
    artifact = libs.com.google.protobuf.protoc.get().toString()
  }
  generateProtoTasks {
    all().forEach { task ->
      task.builtins {
        create("java") {
          option("lite")
        }
        create("kotlin") {
          option("lite")
        }
      }
    }
  }
}

dependencies {
  api(libs.androidx.datastore.datastore)
  implementation(libs.com.google.protobuf.protobuf.kotlin.lite)
  implementation(projects.core.dispatcher)
  implementation(projects.data.preferences)
}
