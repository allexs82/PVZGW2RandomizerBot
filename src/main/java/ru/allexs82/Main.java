package ru.allexs82;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import org.jetbrains.annotations.NotNull;
import ru.allexs82.event_handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main class to initialize and run the PVZGW2 Randomizer Bot.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String token = tryFindToken(args);

        JDA jda = setupJDA(token);
        if (jda == null) {
            LOGGER.error("Failed to initialize JDA");
            return;
        }

        // Register event listeners
        jda.addEventListener(
                new ReceiveLogger(),
                new RCharEventHandler(),
                new RGameEventHandler(),
                new RModeEventHandler(),
                new CharPacksEventHandler(),
                new HelpEventHandler()
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

    private static String tryFindToken(@NotNull String[] args) {
        File tokenFile = new File("token.txt");
        if (tokenFile.exists()) {
            try (Scanner scanner = new Scanner(tokenFile)) {
                return scanner.nextLine();
            } catch (FileNotFoundException ignored) {}
        }
        if (args.length > 0) {
            return args[0];
        }
        return System.getenv("PVZGW2RandomizerBotToken");
    }

    private static JDA setupJDA(@NotNull String token) {
        JDA jda = null;
        try {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .build()
                    .awaitReady();
        } catch (InterruptedException e) {
            LOGGER.error("Failed to initialize JDA", e);

        } catch (InvalidTokenException e) {
            LOGGER.error("Invalid bot token", e);
            LOGGER.info("Would you like to enter bot token? [y/n]");
            Scanner in = new Scanner(System.in);

            String tokenInput = in.nextLine();
            if (!tokenInput.equalsIgnoreCase("y")) {
                LOGGER.info("You can create token.txt, pass token as argument, setup env var PVZGW2RandomizerBotToken or enter token manually.");
                LOGGER.info("Shutting down...");
                System.exit(0);
            }
            LOGGER.info("Enter bot token: ");

            token = in.nextLine();
            in.close();
            return setupJDA(token);
        }
        return jda;
    }
}
