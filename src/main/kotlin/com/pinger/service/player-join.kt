package com.pinger.service

import com.pinger.utils.NatsConnection
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.json.JSONObject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class PlayerJoinListener : Listener {
  private val nats = NatsConnection

  @EventHandler
  fun onPlayerJoin(event: PlayerJoinEvent) {
    val playerName = event.player.name
    val currentDate = ZonedDateTime.now()

    val timestampz = currentDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

    val responsePayload = JSONObject()
      .put("nickname", playerName)
      .put("event", "join")
      .put("date", timestampz)

    println("[nats-bridge] ${event.player.name} подключился в $timestampz")

    nats.natsConnection.publish(nats.userEventSubject, responsePayload.toString().toByteArray())
  }
}