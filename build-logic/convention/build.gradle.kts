plugins {
    `kotlin-dsl`
}

group = "com.francesc.neoexplorer.buildconvention"

java {
    toolchain {
        val jdkVersion = libs.versions.jdk.version.get().toInt()
        languageVersion.set(JavaLanguageVersion.of(jdkVersion))
    }
}

dependencies {
    compileOnly(libs.com.android.tools.build.gradle)
    compileOnly(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
    compileOnly(libs.dev.zacsweers.metro.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "neoexplorer.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "neoexplorer.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "neoexplorer.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "neoexplorer.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("dependencyInjection") {
            id = "neoexplorer.dependency.injection"
            implementationClass = "DependencyInjectionConventionPlugin"
        }
        register("androidLibraryTest") {
            id = "neoexplorer.android.library.test"
            implementationClass = "AndroidLibraryTestConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "neoexplorer.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
    }
}
