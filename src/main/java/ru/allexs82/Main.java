package ru.allexs82;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import ru.allexs82.event_handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Main class to initialize and run the PVZGW2 Randomizer Bot.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String token = System.getenv("PVZGW2RandomizerBotToken");
        if (token == null || token.isEmpty()) {
            LOGGER.error("Bot token is not set. Please set the PVZGW2RandomizerBotToken environment variable.");
            return;
        }

        JDA jda;
        try {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .build()
                    .awaitReady();
        } catch (InterruptedException e) {
            LOGGER.error("Failed to initialize JDA", e);
            return;
        } catch (InvalidTokenException e) {
            LOGGER.error("Invalid bot token", e);
            return;
        }

        // Register event listeners
        jda.addEventListener(
                new ReceiveLogger(),
                new RCharEventHandler(),
                new RGameEventHandler(),
                new RModeEventHandler(),
                new CharPacksEventHandler()
        );

        // Await user input to terminate
        try (Scanner scanner = new Scanner(System.in)) {
            LOGGER.info("Bot is running. Press enter to shut down.");
            scanner.nextLine();
        }

        // Shutdown
        jda.shutdown();
        try {
            jda.awaitShutdown();
        } catch (InterruptedException e) {
            LOGGER.error("Error while shutting down JDA", e);
            Thread.currentThread().interrupt();
        }

        LOGGER.info("Bot has been shut down.");
    }
}
