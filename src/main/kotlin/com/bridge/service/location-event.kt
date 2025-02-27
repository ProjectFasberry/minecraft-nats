package com.bridge.service

import NatsHelper
import com.bridge.utils.NatsConnection
import io.nats.client.Message
import org.bukkit.Bukkit
import org.json.JSONObject

class LocationEvent(private val conn: NatsConnection) {
  private var response = JSONObject()

  fun getLocation(nickname: String, message: Message) {
    val player = Bukkit.getPlayer(nickname)

    if (player != null && player.isOnline) {
      val location = response
        .put("world", player.location.world)
        .put("x", player.location.x)
        .put("y", player.location.y)
        .put("z", player.location.z)
        .put("yaw", player.location.yaw)
        .put("pitch", player.location.pitch)
        .toString()

      NatsHelper.reply(conn.natsConnection, message.replyTo, location)
    } else {
      NatsHelper.reply(
        conn.natsConnection,
        message.replyTo,
        response.put("error", "Player not online").toString()
      )
    }
  }
}