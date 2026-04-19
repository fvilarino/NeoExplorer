    import com.francesc.neoexplorer.buildconvention.catalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("testImplementation", catalog.findLibrary("junit.junit").get())
                add("testImplementation", catalog.findLibrary("org.jetbrains.kotlinx.kotlinx.coroutines.test").get())
            }
        }
    }
}

