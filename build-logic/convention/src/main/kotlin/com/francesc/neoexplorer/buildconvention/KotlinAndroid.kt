package com.francesc.neoexplorer.buildconvention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension,
) {
    commonExtension.apply {
        compileSdk = Config.Build.CompileSdk

        defaultConfig.apply {
            minSdk = Config.Build.MinSdk
        }
        (this as? ApplicationExtension)?.defaultConfig?.apply {
            targetSdk = Config.Build.TargetSdk
        }
        compileOptions.apply {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
    }
    jvmCompilerOptions {
        freeCompilerArgs.addAll(Config.CompilerArgs.KotlinFreeCompilerArgs)
        jvmTarget.set(jvmTargetVersion)
    }
}

internal fun Project.jvmCompilerOptions(block: KotlinJvmCompilerOptions.() -> Unit) {
    configure<KotlinAndroidProjectExtension> {
        compilerOptions.apply(block)
    }
}
