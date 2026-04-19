plugins {
    id("neoexplorer.kotlin.library")
    id("neoexplorer.dependency.injection")
}

dependencies {
    implementation(projects.core.clock)
}
