package ru.allexs82

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import ru.allexs82.listeners.*
import java.io.File
import java.util.*

object JDAManager {
    private val listeners: List<ListenerAdapter> = listOf(
        LogEventListener(),
        RGameEventListener(),
        RModeEventListener(),
        RCharEventListener(),
        CharacterPacksEventListener(),
        HelpEventListener()
        )
    private var jda: JDA? = null
    private var token: String? = null

    @Synchronized
    fun getJdaInstance(): JDA {
        if (jda == null) {
            jda = setupJDA(tryFindToken())
        }
        return jda!!
    }

    fun setToken(newToken: String) {
        if (jda != null) return
        token = newToken
    }

    private fun setupJDA(token: String): JDA {
        val jdaBuilder = JDABuilder.createDefault(token)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        listeners.forEach { jdaBuilder.addEventListeners(it) }
        return jdaBuilder.build().awaitReady()
    }

    private fun tryFindToken(): String {
        if (token != null) return token!!

        val tokenFile = File("token.txt")
        if (tokenFile.exists()) {
            val scanner = Scanner(tokenFile)
            val result = scanner.nextLine()
            scanner.close()
            return result
        }

        return System.getenv("PVZGW2RandomizerBotToken")
    }
}
