package ru.allexs82.kotlin

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import ru.allexs82.kotlin.listeners.LogListener
import ru.allexs82.kotlin.listeners.RGameEventListener

object JDAManager {
    private val listeners: List<ListenerAdapter> = listOf(
        LogListener(),
        RGameEventListener()
        )
    private var jda: JDA? = null

    @Synchronized
    fun getJdaInstance(): JDA {
        if (jda == null) {
            jda = setupJDA(System.getenv("PVZGW2RandomizerBotToken"))
        }
        return jda!!
    }

    private fun setupJDA(token: String): JDA {
        return JDABuilder.createDefault(token)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(listeners)
            .build()
            .awaitReady()
    }
}
