package com.francesc.neoexplorer.buildconvention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureDependencyInjection() {
    dependencies {
        add("implementation", project(":core:injection"))
    }
}
