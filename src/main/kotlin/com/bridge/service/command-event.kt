package com.bridge.service

import NatsHelper
import com.bridge.Main
import com.bridge.utils.NatsConnection
import io.nats.client.Message
import org.bukkit.Bukkit
import org.json.JSONObject

class CommandEvent(private val main: Main, private val nats: NatsConnection) {
  private var response = JSONObject()

  fun executeCommand(command: String, message: Message) {
    Bukkit.getScheduler().runTask(main, Runnable {
      try {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command)

        NatsHelper.reply(
          nats.natsConnection,
          message.replyTo,
          response.put("result", "ok").toString()
        )
      } catch (e: Exception) {
        NatsHelper.reply(
          nats.natsConnection,
          message.replyTo,
          response.put("error", e.message).toString()
        )
      }
    })
  }
}