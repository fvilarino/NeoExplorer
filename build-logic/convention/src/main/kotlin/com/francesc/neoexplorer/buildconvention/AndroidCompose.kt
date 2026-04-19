package com.francesc.neoexplorer.buildconvention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import java.io.File
import kotlin.collections.addAll

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
    jvmCompilerOptions {
        val file = File(rootDir, "app/stability-config.txt")
        freeCompilerArgs.addAll(
            listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=${file.absolutePath}",
            ),
        )
        if (project.findProperty("enableComposeCompilerReports") == "true") {
            val composeReportsDir = "compose_reports"
            freeCompilerArgs.addAll(
                listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                            project.layout.buildDirectory.get().dir(composeReportsDir).asFile.absolutePath,
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                            project.layout.buildDirectory.get().dir(composeReportsDir).asFile.absolutePath,
                ),
            )
        }
    }
}
