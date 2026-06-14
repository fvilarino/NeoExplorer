plugins {
    id("neoexplorer.kotlin.library")
    id("neoexplorer.dependency.injection")
}

dependencies {
    api(libs.com.slack.circuit.circuit.runtime)
    api(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
}

