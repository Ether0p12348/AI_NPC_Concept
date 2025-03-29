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

public class Main {
    // CONFIGURATIONS
    private static Secret secret;
    private static Config config;
    // DEFAULTS CONFIGURATIONS
    private static MoodDefs moodDefs;
    private static OpinionDefs opinionDefs;
    private static PersonalityDefs personalityDefs;
    private static StylesDefs stylesDefs;

    private static AssistantConfig assistantConfig;
    private static PlayerConfig playerConfig; // TODO: setup player config

    // RUN STORAGE
    private static final List<Assistant> ASSISTANT_LIST = new ArrayList<>();
    private static final List<Player> PLAYER_LIST = new ArrayList<>();

    public static Assistant currentAssistant = null;
    public static Player currentPlayer = null;

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

                ASSISTANT_LIST.add(assistant);
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

                PLAYER_LIST.add(player);
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

    public static void onExit() {
        Console.log("Shutting down...");
        // GRACEFUL PROGRAM END
        Console.log("Shut down completed.");
    }

    public static Secret getSecret() {
        return secret;
    }

    public static Config getConfig() {
        return config;
    }

    @NotNull
    public static Defs<? extends Defs.Def> getDefs(Type type) {
        return switch (type) {
            case Type.MOOD -> moodDefs;
            case Type.OPINION -> opinionDefs;
            case Type.PERSONALITY -> personalityDefs;
            case Type.STYLE -> stylesDefs;
        };
    }

    @Nullable
    public static Defs.Def getDef(Type type, String id) {
        Defs<? extends Defs.Def> defs = getDefs(type);

        for (Defs.Def d : defs.getDefs()) {
            if (d.getId().equals(id)) {
                return d;
            }
        }

        return null;
    }

    public static List<Assistant> getAssistants() {
        return ASSISTANT_LIST;
    }

    public static List<Player> getPlayers() {
        return PLAYER_LIST;
    }
}
