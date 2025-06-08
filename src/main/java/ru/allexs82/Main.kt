package ru.allexs82

import org.slf4j.LoggerFactory
import ru.allexs82.utils.CommandSetupUtil

fun main(args: Array<String>) {
    var env = "local"
    args.forEach { arg -> when {
        arg.startsWith("-token=") -> JDAManager.setToken(arg.removePrefix("-token="))
        arg == "-keepAliveOnShutdown" -> JDAManager.exitOnShutdown = false
        arg.startsWith("-env=") -> env = arg.removePrefix("-env=")
    } }

    JDAManager.getJdaInstance()
    val logger = LoggerFactory.getLogger("Main")

    if (env == "render") {
        return
    }

    logger.info("Would you like to setup command (y/n)? It's only required to preform on the first start.")
    if (readln().trim().lowercase() == "y") {
        CommandSetupUtil.setupSlashCommands()
    }
    logger.info("Press enter to shutdown")
    readln()
    JDAManager.getJdaInstance().shutdown()
}
