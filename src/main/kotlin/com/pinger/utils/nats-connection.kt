package com.pinger.utils

import io.github.cdimascio.dotenv.dotenv
import io.nats.client.Connection
import io.nats.client.Nats
import io.nats.client.Subscription

class NatsConnection {
  companion object {
    lateinit var natsConnection: Connection

    const val userStatusSubject = "server.user.status"
    const val userEventSubject = "server.user.event"

    private val dotenv = dotenv()
    private var natsToken = dotenv["NATS_AUTH_TOKEN"]

    fun connect() {
      natsConnection = Nats.connect("nats://$natsToken@localhost:4222")
    }

    fun subscribeToLogin(): Subscription {
      return natsConnection.subscribe(userStatusSubject)
    }

    fun closeConnection() {
      natsConnection.close()
    }
  }
}