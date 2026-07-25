plugins {
  id("neoexplorer.android.application")
  id("neoexplorer.dependency.injection")
}

android {
  namespace = "com.francesc.neoexplorer"

  defaultConfig {
    applicationId = "com.francesc.neoexplorer"
    versionCode = 1
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(libs.androidx.activity.activity.compose)
  implementation(libs.com.slack.circuit.circuit.foundation)
  implementation(projects.core.clock.impl)
  implementation(projects.core.dispatcher.impl)
  implementation(projects.core.formatter.impl)
  implementation(projects.data.neo.impl)
  implementation(projects.data.preferences.impl)
  implementation(projects.ui.feature.browse)
  implementation(projects.ui.feature.dashboard)
  implementation(projects.ui.feature.details)
  implementation(projects.ui.feature.home)
  implementation(projects.ui.feature.settings)
  implementation(projects.ui.feature.temporalexplorer)
  implementation(projects.ui.shared.errormessage)
  implementation(projects.ui.shared.styles)
}
