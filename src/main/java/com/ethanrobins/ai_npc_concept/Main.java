package com.ethanrobins.ai_npc_concept;

import com.ethanrobins.ai_npc_concept.commands.EndCommand;
import com.ethanrobins.ai_npc_concept.commands.HelpCommand;
import com.ethanrobins.ai_npc_concept.commands.SessionCommand;
import com.ethanrobins.ai_npc_concept.configs.*;
import com.ethanrobins.ai_npc_concept.meta.MetaDef;
import com.ethanrobins.ai_npc_concept.meta.MetaScore;
import com.ethanrobins.ai_npc_concept.meta.Sex;
import com.ethanrobins.ai_npc_concept.meta.Type;
import com.ethanrobins.ai_npc_concept.utils.Console;
import com.ethanrobins.ai_npc_concept.utils.LogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * <b>Main Class</b> - The central entry point of the application for initializing and managing the AI NPC concept.
 * <p>This class sets up the environment, configurations, and runtime storage required for simulating and interacting
 * with AI-driven NPCs (Non-Player Characters). It also provides utility methods and fields for managing assistants
 * and players during runtime.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link Main#secret}</li>
 *   <li>{@link Main#config}</li>
 *   <li>{@link Main#moodDefs}</li>
 *   <li>{@link Main#opinionDefs}</li>
 *   <li>{@link Main#personalityDefs}</li>
 *   <li>{@link Main#stylesDefs}</li>
 *   <li>{@link Main#assistantConfig}</li>
 *   <li>{@link Main#playerConfig}</li>
 *   <li>{@link Main#assistantList}</li>
 *   <li>{@link Main#playerList}</li>
 *   <li>{@link Main#currentAssistant}</li>
 *   <li>{@link Main#currentPlayer}</li>
 * </ul>
 *
 * <b>Methods:</b>
 * <ul>
 *   <li>{@link Main#main(String[])}</li>
 *   <li>{@link Main#onExit()}</li>
 *   <li>{@link Main#getSecret()}</li>
 *   <li>{@link Main#getConfig()}</li>
 *   <li>{@link Main#getDefs(Type)}</li>
 *   <li>{@link Main#getDef(Type, String)}</li>
 *   <li>{@link Main#getAssistants()}</li>
 *   <li>{@link Main#getPlayers()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Run this class to initialize the application and begin managing the AI NPC concept. Use the fields and methods
 * for configuration, runtime interactions, and accessing the assistants or players data.</p>
 */
public class Main {
    // CONFIGURATIONS
    /**
     * Stores the secret configuration values used across the application.
     */
    private static Secret secret;
    /**
     * Global configuration object for defining application-wide settings,
     */
    private static Config config;
    // DEFAULTS CONFIGURATIONS
    /**
     * Contains all mood-related definitions used to shape the emotional attributes
     * of assistants and their interactions.
     *
     * @see Type#MOOD
     */
    private static MoodDefs moodDefs;
    /**
     * Contains all opinion definitions that influence how assistants react or behave
     * toward different inputs or events.
     *
     * @see Type#OPINION
     */
    private static OpinionDefs opinionDefs;
    /**
     * Houses the personality trait definitions that define the behavioral disposition
     * of assistants.
     *
     * @see Type#PERSONALITY
     */
    private static PersonalityDefs personalityDefs;
    /**
     * Contains the various communication and expression styles that assistants
     * can utilize during interactions.
     *
     * @see Type#STYLE
     */
    private static StylesDefs stylesDefs;

    /**
     * Configuration object that holds definitions for each assistant in the system.
     */
    private static AssistantConfig assistantConfig;
    /**
     * Configuration object that holds definitions for each player in the system.
     */
    private static PlayerConfig playerConfig;

    // RUN STORAGE
    /**
     * A list containing all assistant entities loaded from the configuration.
     * This serves as the in-memory registry of AI-driven NPCs available for interaction.
     */
    private static final List<Assistant> assistantList = new ArrayList<>();
    /**
     * A list containing all player entities loaded from the configuration.
     * This holds the registered players recognized by the system at runtime.
     */
    private static final List<Player> playerList = new ArrayList<>();

    /**
     * The currently active assistant during runtime. Used as the focal point for interactions.
     */
    public static Assistant currentAssistant = null;
    /**
     * The currently active player during runtime. Used as the interacting user entity.
     */
    public static Player currentPlayer = null;

    /**
     * The entry point of the application.
     * <p>Initializes configurations, loads assistants and players, and starts the CLI interface.
     * Also sets up command handlers and assigns default assistant/player from config.</p>
     *
     * @param args Command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        // CONFIGURATIONS
        try {
            Console.Progress configProgress = Console.progress("Loading Configurations", 8);
            secret = (Secret) ConfigType.SECRET.get();
            configProgress.setProgressMessage("Loaded Secret config (secret.json)").step();
            config = (Config) ConfigType.CONFIG.get();
            configProgress.setProgressMessage("Loaded Main config (config.json").step();
            moodDefs = (MoodDefs) ConfigType.MOOD_DEFS.get();
            configProgress.setProgressMessage("Loaded Mood definitions (tags/mood.json").step();
            opinionDefs = (OpinionDefs) ConfigType.OPINION_DEFS.get();
            configProgress.setProgressMessage("Loaded Opinion definitions (tags/opinion.json").step();
            personalityDefs = (PersonalityDefs) ConfigType.PERSONALITY_DEFS.get();
            configProgress.setProgressMessage("Loaded Personality definitions (tags/personality.json").step();
            stylesDefs = (StylesDefs) ConfigType.STYLES_DEFS.get();
            configProgress.setProgressMessage("Loaded Speech Style definitions (tags/styles.json").step();
            assistantConfig = (AssistantConfig) ConfigType.ASSISTANTS_CONFIG.get();
            configProgress.setProgressMessage("Loaded Assistants config (assistants.json").step();
            playerConfig = (PlayerConfig) ConfigType.PLAYER_CONFIG.get();
            configProgress.setProgressMessage("Loaded Players config (players.json").step();
        } catch (IOException e) {
            Console.error(e);
        }

        // INIT
        if (config.getLogging().getLevel().equals("DEBUG")) {
            Console.logLevel = LogLevel.DEBUG;
        } else {
            Console.logLevel = LogLevel.INFO;
        }

        // SESSION DATA
        Console.Progress assistantProgress = Console.progress("Loading Assistants", assistantConfig.getAssistants().size());
        for (AssistantConfig.Assistant a : assistantConfig.getAssistants()) {
            try {
                Sex sex = switch (a.getSex()) {
                    case "MALE" -> Sex.MALE;
                    case "FEMALE" -> Sex.FEMALE;
                    default -> Sex.UNKNOWN;
                };

                String description = a.getDescription()
                        .replaceAll(Pattern.quote("#{age}"), String.valueOf(a.getAge()))
                        .replaceAll(Pattern.quote("#{sex}"), sex.getDescription())
                        .replaceAll(Pattern.quote("#{profession}"), a.getProfession()) // TODO: May eventually be mutable.
                        .replaceAll(Pattern.quote("#{name}"), a.getName())
                        .replaceAll(Pattern.quote("#{description}"), a.getDescription());

                Assistant.Personality personality = new Assistant.Personality(
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "001")).getId(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "001")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "001")).getName(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "001")).getDescription(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "001"))).getScores().getLow(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "001"))).getScores().getNeutral(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "001"))).getScores().getHigh()
                                ),
                                a.getPersonality().getEmpathy()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "002")).getId(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "002")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "002")).getName(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "002")).getDescription(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "002"))).getScores().getLow(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "002"))).getScores().getNeutral(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "002"))).getScores().getHigh()
                                ),
                                a.getPersonality().getDiscipline()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "003")).getId(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "003")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "003")).getName(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "003")).getDescription(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "003"))).getScores().getLow(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "003"))).getScores().getNeutral(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "003"))).getScores().getHigh()
                                ),
                                a.getPersonality().getConfidence()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "004")).getId(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "004")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "004")).getName(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "004")).getDescription(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "004"))).getScores().getLow(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "004"))).getScores().getNeutral(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "004"))).getScores().getHigh()
                                ),
                                a.getPersonality().getTemper()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "005")).getId(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "005")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "005")).getName(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "005")).getDescription(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "005"))).getScores().getLow(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "005"))).getScores().getNeutral(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "005"))).getScores().getHigh()
                                ),
                                a.getPersonality().getCuriosity()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "006")).getId(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "006")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "006")).getName(),
                                        Objects.requireNonNull(getDef(Type.PERSONALITY, "006")).getDescription(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "006"))).getScores().getLow(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "006"))).getScores().getNeutral(),
                                        ((PersonalityDefs.Personality) Objects.requireNonNull(getDef(Type.PERSONALITY, "006"))).getScores().getHigh()
                                ),
                                a.getPersonality().getHumor()
                        )
                );

                List<MetaDef> styles = new ArrayList<>();
                styles.add(new MetaDef(
                        Objects.requireNonNull(getDef(Type.STYLE, "001")).getId(),
                        Objects.requireNonNull(getDef(Type.STYLE, "001")).getIdentifier(),
                        Objects.requireNonNull(getDef(Type.STYLE, "001")).getName(),
                        Objects.requireNonNull(getDef(Type.STYLE, "001")).getDescription(),
                        null, null, null
                ));
                styles.add(new MetaDef(
                        Objects.requireNonNull(getDef(Type.STYLE, "002")).getId(),
                        Objects.requireNonNull(getDef(Type.STYLE, "002")).getIdentifier(),
                        Objects.requireNonNull(getDef(Type.STYLE, "002")).getName(),
                        Objects.requireNonNull(getDef(Type.STYLE, "002")).getDescription(),
                        null, null, null
                ));
                styles.add(new MetaDef(
                        Objects.requireNonNull(getDef(Type.STYLE, "003")).getId(),
                        Objects.requireNonNull(getDef(Type.STYLE, "003")).getIdentifier(),
                        Objects.requireNonNull(getDef(Type.STYLE, "003")).getName(),
                        Objects.requireNonNull(getDef(Type.STYLE, "003")).getDescription(),
                        null, null, null
                ));
                styles.add(new MetaDef(
                        Objects.requireNonNull(getDef(Type.STYLE, "004")).getId(),
                        Objects.requireNonNull(getDef(Type.STYLE, "004")).getIdentifier(),
                        Objects.requireNonNull(getDef(Type.STYLE, "004")).getName(),
                        Objects.requireNonNull(getDef(Type.STYLE, "004")).getDescription(),
                        null, null, null
                ));
                styles.add(new MetaDef(
                        Objects.requireNonNull(getDef(Type.STYLE, "005")).getId(),
                        Objects.requireNonNull(getDef(Type.STYLE, "005")).getIdentifier(),
                        Objects.requireNonNull(getDef(Type.STYLE, "005")).getName(),
                        Objects.requireNonNull(getDef(Type.STYLE, "005")).getDescription(),
                        null, null, null
                ));
                styles.add(new MetaDef(
                        Objects.requireNonNull(getDef(Type.STYLE, "006")).getId(),
                        Objects.requireNonNull(getDef(Type.STYLE, "006")).getIdentifier(),
                        Objects.requireNonNull(getDef(Type.STYLE, "006")).getName(),
                        Objects.requireNonNull(getDef(Type.STYLE, "006")).getDescription(),
                        null, null, null
                ));
                styles.add(new MetaDef(
                        Objects.requireNonNull(getDef(Type.STYLE, "007")).getId(),
                        Objects.requireNonNull(getDef(Type.STYLE, "007")).getIdentifier(),
                        Objects.requireNonNull(getDef(Type.STYLE, "007")).getName(),
                        Objects.requireNonNull(getDef(Type.STYLE, "007")).getDescription(),
                        null, null, null
                ));

                List<Assistant.StyleOverride> styleOverrides = new ArrayList<>();
                a.getStyleOverrides().forEach((k, v) -> {
                    List<MetaDef> overriddenStyles = new ArrayList<>();
                    for (String str : v) {
                        StylesDefs.Style s = stylesDefs.getDefByIdentifier(str);
                        overriddenStyles.add(new MetaDef(
                                s.getId(),
                                s.getIdentifier(),
                                s.getName(),
                                s.getDescription(),
                                null, null, null
                        ));
                    }

                    styleOverrides.add(new Assistant.StyleOverride(
                            Assistant.OverrideCondition.fromString(k),
                            overriddenStyles
                    ));
                });

                Assistant.Mood mood = new Assistant.Mood(
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.MOOD, "001")).getId(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "001")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "001")).getName(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "001")).getDescription(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "001"))).getScores().getLow(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "001"))).getScores().getNeutral(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "001"))).getScores().getHigh()
                                ),
                                a.getDefaults().getMood().getSocialEnergy()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.MOOD, "002")).getId(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "002")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "002")).getName(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "002")).getDescription(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "002"))).getScores().getLow(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "002"))).getScores().getNeutral(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "002"))).getScores().getHigh()
                                ),
                                a.getDefaults().getMood().getTrustInOthers()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.MOOD, "003")).getId(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "003")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "003")).getName(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "003")).getDescription(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "003"))).getScores().getLow(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "003"))).getScores().getNeutral(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "003"))).getScores().getHigh()
                                ),
                                a.getDefaults().getMood().getEmotionalStability()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.MOOD, "004")).getId(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "004")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "004")).getName(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "004")).getDescription(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "004"))).getScores().getLow(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "004"))).getScores().getNeutral(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "004"))).getScores().getHigh()
                                ),
                                a.getDefaults().getMood().getMotivation()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.MOOD, "005")).getId(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "005")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "005")).getName(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "005")).getDescription(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "005"))).getScores().getLow(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "005"))).getScores().getNeutral(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "005"))).getScores().getHigh()
                                ),
                                a.getDefaults().getMood().getPride()
                        ),
                        new MetaScore(
                                new MetaDef(
                                        Objects.requireNonNull(getDef(Type.MOOD, "006")).getId(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "006")).getIdentifier(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "006")).getName(),
                                        Objects.requireNonNull(getDef(Type.MOOD, "006")).getDescription(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "006"))).getScores().getLow(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "006"))).getScores().getNeutral(),
                                        ((MoodDefs.Mood) Objects.requireNonNull(getDef(Type.MOOD, "006"))).getScores().getHigh()
                                ),
                                a.getDefaults().getMood().getAttachmentToOthers()
                        )
                );

                Assistant assistant = new Assistant(
                        a.getId(),
                        a.getName(),
                        a.getProfession(),
                        a.getAge(),
                        sex,
                        description,
                        personality,
                        styles,
                        a.getDefaults().getEmotionalState(),
                        mood,
                        null, // To be set when a player first interacts with player using defaults and eventually the player's local reputation.
                        a.getTemperature(),
                        styleOverrides
                );
                assistantProgress.setProgressMessage("Loaded Assistant: " + a.getName() + " (" + a.getId() + ")");

                assistantList.add(assistant);
                assistantProgress.step();
            } catch (Exception e) {
                assistantProgress.error(e);
            }
        }
        Console.Progress playerProgress = Console.progress("Loading Players", playerConfig.getPlayers().size());
        for (PlayerConfig.Player p : playerConfig.getPlayers()) {
            try {
                Sex sex = switch (p.getSex()) {
                    case "MALE" -> Sex.MALE;
                    case "FEMALE" -> Sex.FEMALE;
                    default -> Sex.UNKNOWN;
                };

                Player player = new Player(
                        p.getId(),
                        p.getName(),
                        p.getProfession(),
                        p.getAge(),
                        sex
                );

                playerProgress.setProgressMessage("Loaded Player: " + p.getName() + " (" + p.getId() + ")");

                playerList.add(player);
                playerProgress.step();
            } catch (Exception e) {
                playerProgress.error(e);
            }
        }

        // OPENAI API INITIALIZATION
        //Console.log("Initializing OpenAI API...");
        Console.warn("Holding off on initializing OpenAI API.");
        // TODO: Create assistants for this project based on Assistant list (hold off on this for now)

        // DEFAULTS
        Console.Progress defaultsProgress = Console.progress("Loading Defaults", 2);
        try {
            Thread.sleep(500);
            currentAssistant = getAssistants().stream().filter(a -> a.getId().equals(config.getDefaults().getAssistantId())).toList().getFirst();
            defaultsProgress.step();
            currentPlayer = getPlayers().stream().filter(p -> p.getId().equals(config.getDefaults().getAssistantId())).toList().getFirst();
            defaultsProgress.step();
        } catch (Exception e) {
            defaultsProgress.error(e);
        }

        // CLI
        Console.startCli();
        // Command Registry
        new HelpCommand();
        new EndCommand();
        new SessionCommand();
    }

    /**
     * Executes application shutdown logic in a graceful manner.
     * <p>Logs the shutdown process and performs any required cleanup or exit handling.</p>
     */
    public static void onExit() {
        Console.log("Shutting down...");
        // GRACEFUL PROGRAM END
        Console.log("Shut down completed.");
    }

    /**
     * Retrieves the global {@link Secret} configuration object.
     *
     * @return {@link Main#secret}
     */
    public static Secret getSecret() {
        return secret;
    }

    /**
     * Retrieves the main {@link Config} object for accessing runtime configuration values.
     *
     * @return {@link Main#config}
     */
    public static Config getConfig() {
        return config;
    }

    /**
     * Retrieves the {@link Defs} container corresponding to the specified {@link Type}.
     *
     * <br><b>Behavior:</b>
     * <ul>
     *   <li>Returns {@link MoodDefs}, {@link OpinionDefs}, {@link PersonalityDefs}, or {@link StylesDefs}
     *       depending on the provided {@link Type}.</li>
     * </ul>
     *
     * @param type The {@link Type} to fetch definitions for.
     * @return The {@link Defs} corresponding to the given {@link Type}.
     */
    @NotNull
    public static Defs<? extends Defs.Def> getDefs(@NotNull Type type) {
        return switch (type) {
            case Type.MOOD -> moodDefs;
            case Type.OPINION -> opinionDefs;
            case Type.PERSONALITY -> personalityDefs;
            case Type.STYLE -> stylesDefs;
        };
    }

    /**
     * Retrieves a single definition object by its ID and type.
     *
     * <br><b>Behavior:</b>
     * <ul>
     *   <li>Searches through the definitions provided by {@link #getDefs(Type)}.</li>
     *   <li>If a matching ID is found, it is returned. Otherwise, returns {@code null}.</li>
     * </ul>
     *
     * @param type The {@link Type} to narrow the definition search space.
     * @param id The string ID of the definition to look up.
     * @return The matching {@link Defs.Def} if found, or {@code null} if not.
     */
    @Nullable
    public static Defs.Def getDef(@NotNull Type type, @NotNull String id) {
        Defs<? extends Defs.Def> defs = getDefs(type);

        for (Defs.Def d : defs.getDefs()) {
            if (d.getId().equals(id)) {
                return d;
            }
        }

        return null;
    }

    /**
     * Returns a list of all {@link Assistant} entities loaded during startup.
     *
     * @return {@link Main#assistantList}
     */
    public static List<Assistant> getAssistants() {
        return assistantList;
    }

    /**
     * Returns a list of all {@link Player} entities loaded during startup.
     *
     * @return {@link Main#playerList}
     */
    public static List<Player> getPlayers() {
        return playerList;
    }
}
