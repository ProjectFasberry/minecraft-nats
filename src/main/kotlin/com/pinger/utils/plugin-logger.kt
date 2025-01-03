package com.pinger.utils

import java.util.logging.Logger

class PluginLogger() {
  fun onStartPrint(logger: Logger) {
    val text = """
                                                                                  _|                                                            
_|_|_|_|                    _|                                                    _|      _|_|_|    _|                                          
_|        _|_|_|    _|_|_|  _|_|_|      _|_|    _|  _|_|  _|  _|_|  _|    _|      _|      _|    _|      _|_|_|      _|_|_|    _|_|    _|  _|_|  
_|_|_|  _|    _|  _|_|      _|    _|  _|_|_|_|  _|_|      _|_|      _|    _|      _|      _|_|_|    _|  _|    _|  _|    _|  _|_|_|_|  _|_|      
_|      _|    _|      _|_|  _|    _|  _|        _|        _|        _|    _|      _|      _|        _|  _|    _|  _|    _|  _|        _|        
_|        _|_|_|  _|_|_|    _|_|_|      _|_|_|  _|        _|          _|_|_|      _|      _|        _|  _|    _|    _|_|_|    _|_|_|  _|        
                                                                          _|      _|                                    _|                      
                                                                      _|_|        _|                                _|_|            
    """.trimIndent()
    logger.info(text)
  }

  fun onClosePrint(logger: Logger) {
    val text = "[nats-bridge]: NatsBridgePlugin отключен!"
    logger.info(text)
  }

  fun onErrorPrint(e: Exception, logger: Logger) {
    val text = "[nats-bridge]: Ошибка при обработке сообщений NATS: ${e.message}"
    logger.warning(text)
  }
}