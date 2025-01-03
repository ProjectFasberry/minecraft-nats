package com.pinger.service

import com.pinger.utils.NatsConnection
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.json.JSONObject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class PlayerQuitListener : Listener {
  private val nats = NatsConnection

  @EventHandler
  fun onPlayerQuit(event: PlayerQuitEvent) {
    val playerName = event.player.name
    val currentDate = ZonedDateTime.now()

    val timestampz = currentDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

    val responsePayload = JSONObject()
      .put("nickname", playerName)
      .put("event", "quit")
      .put("date", timestampz)

    println("[nats-bridge] ${event.player.name} отключился в $timestampz")

    nats.natsConnection.publish(nats.userEventSubject, responsePayload.toString().toByteArray())
  }
}