package com.francesc.neoexplorer.buildconvention

object Config {
    object Build {
        const val VersionName = "1.0.0"
        const val MinSdk = 28
        const val CompileSdk = 36
        const val TargetSdk = 36
    }

    object CompilerArgs {
        val KotlinFreeCompilerArgs = listOf(
            "-opt-in=kotlin.RequiresOptIn",
        )
    }
}
