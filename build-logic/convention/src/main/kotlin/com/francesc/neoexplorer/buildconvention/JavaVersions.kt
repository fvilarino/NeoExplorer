package com.francesc.neoexplorer.buildconvention

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val Project.javaVersion: JavaVersion
    get() = JavaVersion.toVersion(
        catalog.findVersion("jdk.version").get().requiredVersion,
    )

val Project.jvmTargetVersion: JvmTarget
    get() = JvmTarget.fromTarget(
        catalog.findVersion("jdk.version").get().requiredVersion,
    )
