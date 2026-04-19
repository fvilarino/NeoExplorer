package com.francesc.neoexplorer.buildconvention

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

internal fun configureAndroidTest(
    commonExtension: CommonExtension,
) {
    commonExtension.testOptions.apply {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        unitTests.all { test ->
            test.testLogging {
                events("passed", "skipped", "failed")
                showStandardStreams = true
                showStackTraces = true
                showCauses = true
                exceptionFormat = TestExceptionFormat.FULL
            }
        }
    }
}

internal fun LibraryAndroidComponentsExtension.disableUnnecessaryAndroidTests(
    project: Project,
) = beforeVariants { libraryVariantBuilder ->
    libraryVariantBuilder.androidTest.enable = libraryVariantBuilder.androidTest.enable &&
        project.projectDir.resolve("src/androidTest").exists()
}
