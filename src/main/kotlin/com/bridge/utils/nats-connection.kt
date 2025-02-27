package com.bridge.utils

import ConfigManager
import com.bridge.Main
import io.nats.client.Connection
import io.nats.client.Nats
import io.nats.client.Subscription

class NatsConnection(private val main: Main, private val config: ConfigManager) {
  lateinit var natsConnection: Connection
    private set

  companion object {
    const val USER_EVENT_SUBJECT = "server.user.event"
  }

  fun connect() {
    val natsUrl = "nats://${config.natsToken}@${config.natsHost}:${config.natsPort}"

    natsConnection = Nats.connect(natsUrl)
  }

  fun subscribe(): Subscription {
    return try {
      main.getInstance().logger.info("Subscribed ${USER_EVENT_SUBJECT}")

      natsConnection.subscribe(USER_EVENT_SUBJECT) ?: throw Exception("Subscription returned null.")
    } catch (e: Exception) {
      main.getInstance().logger.severe("Error while subscribing: ${e.message}")
      main.disablePlugin()
      throw e
    }
  }

  fun closeConnection() {
    natsConnection.close()
  }
}