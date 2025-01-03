package com.pinger.utils

import java.util.logging.Logger

class PluginLogger() {
  fun onStartPrintLargeName(logger: Logger) {
    val text = """
    FFFFF   AAAAA   SSSSS  BBBBB  EEEEE  RRRRR   RRRRR  YYYYY  
    F       A   A   S       B   B  E      R   R   R   R    Y    
    FFFF    AAAAA   SSSSS   BBBBB  EEEE   RRRRR   RRRRR    Y    
    F       A   A       S   B   B  E      R  R    R  R     Y    
    F       A   A   SSSSS  BBBBB  EEEEE  R   R   R   R     Y    

     ppppp   III  N   N  GGGG  EEEEE  RRRRR  
     p   p    I   NN  N  G     E      R   R  
     ppppp    I   N N N  G  GG  EEEE  RRRR 
     p        I   N  NN  G   G  E     R   R   
     p        I   N   N  GGGG  EEEEE  R   R  
    """.trimIndent()
    logger.info(text)
  }

  fun onClosePrintLargeName(logger: Logger) {
    val text = "NatsBridgePlugin отключен!"
    logger.info(text)
  }
}