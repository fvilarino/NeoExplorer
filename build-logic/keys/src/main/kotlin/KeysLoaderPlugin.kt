import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.util.Properties

private const val NasaApiKey = "nasa_api_key"
private const val NasaKeysFile = "./certs/keys.properties"

open class NasaConfigKeys {
    val nasaApiKey: String
        get() = key ?: error("Can't locate NASA API key. Add '$NasaApiKey' to $NasaKeysFile or set it as an environment variable.")

    private var key: String? = null

    internal fun setKey(key: String) {
        this.key = key
    }
}

class KeysLoaderPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<NasaConfigKeys>("NasaConfigKeys")
        val properties = Properties()
        val keysFile = target.rootProject.file(NasaKeysFile)
        if (keysFile.exists()) {
            keysFile.inputStream().use { stream ->
                properties.load(stream)
            }
        }
        val key = properties.getProperty(NasaApiKey) ?: System.getenv(NasaApiKey)
        if (key != null) {
            extension.setKey(key)
        }
    }
}

