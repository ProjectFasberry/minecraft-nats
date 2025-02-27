package com.bridge.service

import com.bridge.utils.NatsConnection
import io.nats.client.Message
import org.bukkit.Bukkit
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class PingerEvent(private val nats: NatsConnection) {
  fun checkPlayerStatus(nickname: String, message: Message) {
    val player = Bukkit.getPlayer(nickname)

    val responsePayload = JSONObject()
      .put("nickname", nickname)
      .put("type", if (player != null && player.isOnline) "online" else "offline")

    val responseBytes = responsePayload
      .toString()
      .toByteArray(StandardCharsets.UTF_8)

    if (message.replyTo != null) {
      nats.natsConnection.publish(message.replyTo, responseBytes)
    }
  }
}