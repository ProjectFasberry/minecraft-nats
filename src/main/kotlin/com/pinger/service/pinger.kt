package com.pinger.service

import com.pinger.utils.NatsConnection
import io.nats.client.Message
import io.nats.client.Subscription
import org.bukkit.Bukkit
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class PlayerPinger(private val nats: NatsConnection.Companion) {
  fun processNatsMessages(subscription: Subscription) {
    try {
      while (true) {
        val message = subscription.nextMessage(1000)

        if (message != null) {
          val payload = JSONObject(String(message.data, StandardCharsets.UTF_8))
          val nickname = payload.getString("nickname")

          checkPlayerStatus(nickname, message)
        }
      }
    } catch (e: Exception) {
      println("[nats-bridge]: Ошибка при обработке NATS сообщений: ${e.message}")
    }
  }

  private fun checkPlayerStatus(nickname: String, message: Message) {
    val player = Bukkit.getPlayer(nickname)

    val responsePayload = JSONObject()
      .put("nickname", nickname)
      .put("type", if (player != null && player.isOnline) "online" else "offline")

    val responseBytes = responsePayload
      .toString()
      .toByteArray(StandardCharsets.UTF_8)

    val replyTo = message.replyTo

    if (replyTo != null) {
      nats.natsConnection.publish(replyTo, responseBytes)
    }
  }
}