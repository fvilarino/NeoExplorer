buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.com.android.tools.build.gradle)
        classpath(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.com.google.devtools.ksp) apply false
    alias(libs.plugins.dev.zacsweers.metro) apply false
    alias(libs.plugins.org.jetbrains.kotlin.compose.compiler) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory.get())
}
