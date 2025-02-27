package com.bridge.utils

import java.util.logging.Logger

class PluginLogger() {
  fun onClosePrint(logger: Logger) {
    val text = "NatsBridgePlugin is currently disabled!"
    logger.info(text)
  }
}