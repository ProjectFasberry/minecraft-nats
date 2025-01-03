package com.pinger

import com.pinger.service.PlayerPinger
import com.pinger.utils.NatsConnection
import com.pinger.utils.PluginLogger
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
  private val nats = NatsConnection
  private val pluginLogger = PluginLogger()
  private val playerPinger = PlayerPinger(nats)

  override fun onEnable() {
    pluginLogger.onStartPrint(logger)
    NatsConnection.connect()
    logger.info("[nats-bridge]: Подключено к NATS.")

    val subscription = nats.subscribeToLogin()

    Bukkit.getScheduler().runTaskAsynchronously(this, Runnable {
      playerPinger.processNatsMessages(subscription)
    })
  }

  override fun onDisable() {
    nats.closeConnection()
    pluginLogger.onClosePrint(logger)
    logger.info("[nats-bridge]: Отключено от NATS.")
  }
}