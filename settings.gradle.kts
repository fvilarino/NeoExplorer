pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "NeoExplorer"
include(":app")
include(":core:clock")
include(":core:clock:impl")
include(":core:formatter")
include(":core:formatter:impl")
include(":core:injection")
include(":data:neo")
include(":data:neo:impl")
include(":ui:feature:dashboard")
include(":ui:feature:home")
include(":ui:shared:compose")
include(":ui:shared:styles")
