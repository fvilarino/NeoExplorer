import java.util.Properties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

private const val NasaApiKey = "nasa_api_key"
private const val NasaKeysFile = "./certs/keys.properties"

interface NasaConfigKeys {
  val nasaApiKey: Property<String>
}

class KeysLoaderPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    val extension = target.extensions.create<NasaConfigKeys>("NasaConfigKeys")

    val keyProvider =
      target.providers.provider {
        val keysFile = target.rootProject.file(NasaKeysFile)
        val fileKey =
          if (keysFile.exists()) {
            keysFile.inputStream().use { stream ->
              val properties = Properties()
              properties.load(stream)
              properties.getProperty(NasaApiKey)
            }
          } else {
            null
          }
        fileKey
          ?: System.getenv(NasaApiKey)
          ?: error(
            "Can't locate NASA API key. Add '$NasaApiKey' to $NasaKeysFile or set it as an environment variable."
          )
      }

    extension.nasaApiKey.convention(keyProvider)
  }
}
