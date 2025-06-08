package ru.allexs82

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.session.ShutdownEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import org.slf4j.LoggerFactory
import ru.allexs82.listeners.*
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import kotlin.system.exitProcess


private const val TOKEN_FILE = "token.txt"
private const val TOKEN_ENV_VAR = "PVZGW2RandomizerBotToken"

private val logger = LoggerFactory.getLogger(JDAManager::class.java)
private val discordTokenCheckUri = URI.create("https://discord.com/api/v10/users/@me")

object JDAManager {
    val httpClient: HttpClient = HttpClient.newBuilder().build()
    
    var exitOnShutdown = true
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
        if (!isJdaValid()) {
            jda = setupJDA(tryFindToken())
        }
        return jda!!
    }

    fun setToken(newToken: String) {
        if (jda != null) return
        token = newToken
    }

    fun isJdaValid(): Boolean = jda != null

    private fun setupJDA(token: String): JDA {
        val jdaBuilder = JDABuilder.createDefault(token)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)

        jdaBuilder.addEventListeners(object : ListenerAdapter() {
            override fun onShutdown(event: ShutdownEvent) {
                logger.info("JDA instance has been shutdown.")
                jda = null
                if (exitOnShutdown) exitProcess(0)
            }
        })

        listeners.forEach { jdaBuilder.addEventListeners(it) }
        return jdaBuilder.build().awaitReady()
    }

    private fun tryFindToken(): String {
        token?.let { if (checkToken(it)) return it }

        File(TOKEN_FILE).takeIf { it.exists() && it.length() > 0 }?.let { file ->
            file.useLines { lines ->
                lines.firstOrNull()?.takeIf { checkToken(it) }?.let { return it }
            }
        }

        System.getenv(TOKEN_ENV_VAR)?.takeIf { checkToken(it) }?.let { return it }

        throw IllegalArgumentException("""
            No valid token found. Please set token via:
            1. "-token=" cmd argument (example: "java -jar PVZGW2RandomizerBot.jar -token=Yu45foi!sdsx7duIUSD15")
            2. $TOKEN_FILE file
            3. $TOKEN_ENV_VAR environment variable
        """.trimIndent())
    }

    private fun checkToken(token: String): Boolean {
        val request = HttpRequest.newBuilder(discordTokenCheckUri)
            .timeout(Duration.ofSeconds(20))
            .header("Authorization", "Bot $token")
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(Charsets.UTF_8))
        return response.statusCode() == 200
    }
}
