plugins {
    `kotlin-dsl`
}
group = "com.francesc.neoexplorer.keys"
java {
    toolchain {
        val jdkVersion = libs.versions.jdk.version.get().toInt()
        languageVersion.set(JavaLanguageVersion.of(jdkVersion))
    }
}
gradlePlugin {
    plugins {
        register("keysLoader") {
            id = "neoexplorer.keys.loader"
            implementationClass = "KeysLoaderPlugin"
        }
    }
}
