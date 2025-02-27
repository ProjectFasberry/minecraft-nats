import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

class ConfigManager(private val plugin: Plugin) {
  private val configFile = File(plugin.dataFolder, "config.yml")
  private lateinit var config: FileConfiguration

  var natsHost: String = "localhost"
    private set
  var natsPort: Int = 4222
    private set
  var natsToken: String = "token"
    private set

  fun loadConfig() {
    if (!plugin.dataFolder.exists()) {
      plugin.dataFolder.mkdirs()
    }

    if (!configFile.exists()) {
      plugin.saveResource("config.yml", false)
      plugin.logger.info("config.yml был создан.")
    }

    config = YamlConfiguration.loadConfiguration(configFile)

    natsHost = config.getString("nats.host", natsHost) ?: natsHost
    natsPort = config.getInt("nats.port", natsPort)
    natsToken = config.getString("nats.token", natsToken) ?: natsToken

    if (!configFile.exists()) {
      saveDefaults()
    }
  }

  private fun saveDefaults() {
    config.set("nats.host", natsHost)
    config.set("nats.port", natsPort)
    config.set("nats.token", natsToken)
    config.save(configFile)
  }
}