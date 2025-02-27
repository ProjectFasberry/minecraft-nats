package com.bridge.service

import NatsHelper
import com.bridge.utils.NatsConnection
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.json.JSONObject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class PlayerEventListener(private val nats: NatsConnection) : Listener {
  @EventHandler
  fun onPlayerJoin(event: PlayerJoinEvent) {
    handlePlayerEvent(event, "join")
  }

  @EventHandler
  fun onPlayerQuit(event: PlayerQuitEvent) {
    handlePlayerEvent(event, "quit")
  }

  private fun handlePlayerEvent(event: PlayerEvent, eventType: String) {
    val playerName = event.player.name
    val timestamp = ZonedDateTime
      .now()
      .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

    val responsePayload = JSONObject()
      .put("nickname", playerName)
      .put("event", eventType)
      .put("date", timestamp)
      .toString()

    NatsHelper.publish(
      nats.natsConnection,
      NatsConnection.USER_EVENT_SUBJECT,
      responsePayload
    )
  }
}