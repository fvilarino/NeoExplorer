import com.android.build.api.dsl.ApplicationExtension
import com.francesc.neoexplorer.buildconvention.configureAndroidApplication
import com.francesc.neoexplorer.buildconvention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("com.google.devtools.ksp")
            }
            extensions.configure<ApplicationExtension> {
                configureAndroidApplication(this)
                configureKotlinAndroid(this)
            }
        }
    }
}
