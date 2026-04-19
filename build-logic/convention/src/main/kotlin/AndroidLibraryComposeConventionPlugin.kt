import com.android.build.api.dsl.LibraryExtension
import com.francesc.neoexplorer.buildconvention.catalog
import com.francesc.neoexplorer.buildconvention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("kotlin-parcelize")
            pluginManager.apply(
                catalog.findPlugin("org.jetbrains.kotlin.compose.compiler").get().get().pluginId,
            )
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}
