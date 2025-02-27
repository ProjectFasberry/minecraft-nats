package com.bridge.utils

import ConfigManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandsManager(private val configManager: ConfigManager) : CommandExecutor {
  override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
    if (command.name.equals("natsbridge", ignoreCase = true)) {
      if (args.isNotEmpty() && args[0].equals("reload", ignoreCase = true)) {
        configManager.loadConfig()
        sender.sendMessage("[NatsBridge] Конфигурация перезагружена.")
        return true
      } else {
        sender.sendMessage("[NatsBridge] Использование: /natsbridge reload")
        return true
      }
    }

    return false
  }
}