import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("neoexplorer.android.library")
                apply("neoexplorer.dependency.injection")
            }
            dependencies {
                add("implementation", project(":ui:shared:styles"))
            }
        }
    }
}
