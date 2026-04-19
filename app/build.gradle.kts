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
    implementation(projects.ui.feature.home)
    implementation(projects.ui.shared.styles)
    implementation(libs.androidx.activity.activity.compose)
}
