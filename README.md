# Plants vs. Zombies Garden Warfare 2 randomizer discord bot

A feature-rich Discord bot built using Kotlin and the [JDA](https://github.com/discord-jda/JDA) library. This bot is designed to be modular, easy to extend, and packed with useful commands.

## Prerequisites

Before running the bot, ensure you have the following installed:

- [Java JDK 8 or higher](https://adoptium.net/)
- [Kotlin](https://kotlinlang.org/)

## Setup

1. **Clone the repository**:
2. **Configure the bot**:
   - Setup env var called `PVZGW2RandomizerBotToken` with your token or put `token.txt`.
   - Token can be obtained on the [Discord Developer Portal](https://discord.com/developers). You need bot with `applications.commands` scope.
3. **Build and run the bot**:
   - Using Gradle:
     ```bash
     ./gradlew build
     ./gradlew run
     ```

## Commands

Here are the commands available:

| Command             | Description                                    | Example           |
|---------------------|------------------------------------------------|-------------------|
| `/char_pack count`  | Roll a random character from selected pack     | `/char_pack 3`    |
| `/rmode`            | Roll a random map for specified mods           | `/rmode`          |
| `/rchar`            | Roll a random character for selected side      | `/rchar Zombies`  |
| `/rgame p2, p3, p4` | Roll a game setup (/rmode and /rchar combined) | `/rgame @JhonDoe` |
| `/help`             | Shows help                                     | `/help`           |

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
