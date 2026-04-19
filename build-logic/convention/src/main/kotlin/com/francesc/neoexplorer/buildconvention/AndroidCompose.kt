package com.francesc.neoexplorer.buildconvention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension,
) {
    commonExtension.apply {
        buildFeatures.apply {
            compose = true
        }
        dependencies {
            val bom = catalog.findLibrary("androidx.compose.compose.bom").get()
            add("implementation", platform(bom))
            add("implementation", catalog.findBundle("compose").get())
            add("implementation", catalog.findLibrary("androidx.compose.ui.ui.tooling.preview").get())
            add("debugImplementation", catalog.findLibrary("androidx.compose.ui.ui.tooling").get())
            add("debugImplementation", catalog.findLibrary("androidx.compose.ui.ui.test.manifest").get())
        }
    }
}
