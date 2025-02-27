package com.bridge

import ConfigManager
import com.bridge.utils.EventsHandler
import com.bridge.service.PlayerEventListener
import com.bridge.utils.CommandsManager
import com.bridge.utils.NatsConnection
import com.bridge.utils.PluginLogger
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
  private lateinit var nats: NatsConnection
  private lateinit var pluginLogger: PluginLogger
  private lateinit var configManager: ConfigManager
  private lateinit var eventsHandler: EventsHandler
  private lateinit var instance: Main

  override fun onEnable() {
    instance = this

    pluginLogger = PluginLogger()
    configManager = ConfigManager(this)
    configManager.loadConfig()

    this.getCommand("natsbridge")?.setExecutor(CommandsManager(configManager))

    try {
      nats = NatsConnection(instance, configManager)
      nats.connect()
      logger.info("Connected to NATS.")

      val subscription = nats.subscribe()
      eventsHandler = EventsHandler(instance, nats)

      Bukkit.getScheduler().runTaskAsynchronously(this, Runnable {
        eventsHandler.processNatsMessages(subscription)
      })

      server.pluginManager.registerEvents(PlayerEventListener(nats), this)
    } catch (e: Exception) {
      logger.severe("${e.message}")
      disablePlugin()
    }
  }

  fun getInstance(): Main {
    return instance
  }

  fun disablePlugin() {
    val pluginManager: PluginManager = Bukkit.getPluginManager()
    pluginManager.disablePlugin(this)
  }

  override fun onDisable() {
    nats.closeConnection()
    pluginLogger.onClosePrint(logger)
  }
}