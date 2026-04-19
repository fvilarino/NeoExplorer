import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.api.dsl.LibraryExtension
import com.francesc.neoexplorer.buildconvention.catalog
import com.francesc.neoexplorer.buildconvention.configureKotlinAndroid
import com.francesc.neoexplorer.buildconvention.disableUnnecessaryAndroidTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("com.google.devtools.ksp")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
                add("implementation", catalog.findLibrary("androidx.core.core.ktx").get())
                add("implementation", catalog.findLibrary("com.jakewharton.timber").get())
                val bom = catalog.findLibrary("androidx.compose.compose.bom").get()
                add("implementation", platform(bom))
            }
        }
    }
}
