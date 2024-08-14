package ru.allexs82

import org.slf4j.LoggerFactory

fun main() {
    JDAManager.getJdaInstance()
    LoggerFactory.getLogger("Main").info("Press enter to shutdown")
    readln()
    JDAManager.getJdaInstance().shutdown()
}
