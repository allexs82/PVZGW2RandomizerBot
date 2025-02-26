package ru.allexs82

import org.slf4j.LoggerFactory
import ru.allexs82.utils.CommandSetupUtil

fun main() {
    JDAManager.getJdaInstance()
    val logger = LoggerFactory.getLogger("Main")
    logger.info("Would you like to setup command (y/n)? It's only required to preform on the first start.")
    if (readln().trim().lowercase() == "y") {
        CommandSetupUtil.setupSlashCommands()
    }
    logger.info("Press enter to shutdown")
    readln()
    JDAManager.getJdaInstance().shutdown()
}
