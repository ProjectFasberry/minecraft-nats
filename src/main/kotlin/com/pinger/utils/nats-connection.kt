package com.pinger

import io.github.cdimascio.dotenv.dotenv
import io.nats.client.Connection
import io.nats.client.Nats
import io.nats.client.Subscription

class NatsConnection () {
  companion object {
    lateinit var natsConnection: Connection

    val loginSubject = "server.ping.get" // subj for getting req messages
    val offlineSubject = "server.ping.payload" // subj for publish responses

    private val dotenv = dotenv()
    private var natsToken = dotenv["NATS_AUTH_TOKEN"]

    fun connect() {
      natsConnection = Nats.connect("nats://$natsToken@localhost:4222")
    }

    fun subscribeToLogin(): Subscription {
      return natsConnection.subscribe(loginSubject)
    }

    fun closeConnection() {
      natsConnection.close()
    }
  }
}