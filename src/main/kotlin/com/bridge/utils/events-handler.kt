package com.bridge.utils

import com.bridge.service.LocationEvent
import NatsHelper
import com.bridge.Main
import com.bridge.service.CommandEvent
import com.bridge.service.PingerEvent
import io.nats.client.Message
import io.nats.client.Subscription
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors

private val executor = Executors.newSingleThreadExecutor()

class EventsHandler(private val main: Main, private val nats: NatsConnection) {
  private val commandEvent = CommandEvent(main, nats)
  private val pingerEvent = PingerEvent(nats)
  private val locationEvent = LocationEvent(nats)

  private var response: JSONObject = JSONObject()

  fun processNatsMessages(subscription: Subscription) {
    executor.submit {
      try {
        while (!Thread.currentThread().isInterrupted) {
          val message = subscription.nextMessage(1000)

          message?.let {
            handleMessage(it)
          }
        }
      } catch (e: Exception) {
        main.logger.severe("Error processing NATS messages: ${e.message}")
      }
    }
  }

  private fun handleMessage(message: Message) {
    val payload = JSONObject(String(message.data, StandardCharsets.UTF_8))
    val event: String = payload.optString("event", "")

    main.logger.info("${event}, ${payload}")

    if (event in listOf("checkOnline", "getLocation") && !payload.has("nickname")) {
      return NatsHelper.reply(
        nats.natsConnection,
        message.replyTo,
        response.put("error", "This event requires a nickname.").toString()
      )
    }

    if (event == "executeCommand" && !payload.has("command")) {
      return NatsHelper.reply(
        nats.natsConnection,
        message.replyTo,
        response.put("error", "This event requires a command.").toString()
      )
    }

    when (event) {
      "executeCommand" -> {
        val command: String = payload.optString("command", "")
        val allowedPlugins = listOf("cmi", "playerpoints", "p", "lp")

        if (allowedPlugins.none { command.startsWith(it) }) {
          return NatsHelper.reply(
            nats.natsConnection,
            message.replyTo,
            response.put("error", "Command not supported").toString()
          )
        }

        commandEvent.executeCommand(command, message)
      }

      "checkOnline" -> pingerEvent.checkPlayerStatus(payload.getString("nickname"), message)
      "getLocation" -> locationEvent.getLocation(payload.getString("nickname"), message)
    }
  }
}