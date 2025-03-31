package com.ethanrobins.ai_npc_concept;

import com.ethanrobins.ai_npc_concept.meta.MetaDef;
import com.ethanrobins.ai_npc_concept.meta.MetaScore;
import com.ethanrobins.ai_npc_concept.meta.Sex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <b>Assistant Class</b> - Represents an AI-driven assistant entity and its defining properties.
 * <p>This class implements {@link SessionData} and extends it to include additional fields and behaviors specific
 * to the assistant's personality, emotional state, mood, and interactions with styles and opinions.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link Assistant#id}</li>
 *   <li>{@link Assistant#name}</li>
 *   <li>{@link Assistant#profession}</li>
 *   <li>{@link Assistant#age}</li>
 *   <li>{@link Assistant#sex}</li>
 *   <li>{@link Assistant#description}</li>
 *   <li>{@link Assistant#personality}</li>
 *   <li>{@link Assistant#mood}</li>
 *   <li>{@link Assistant#style}</li>
 *   <li>{@link Assistant#temperature}</li>
 *   <li>{@link Assistant#emotionalState}</li>
 *   <li>{@link Assistant#opinions}</li>
 *   <li>{@link Assistant#styleOverrides}</li>
 * </ul>
 * <b>Methods:</b>
 * <ul>
 *   <li>{@link Assistant#getId()}</li>
 *   <li>{@link Assistant#getName()}</li>
 *   <li>{@link Assistant#getProfession()}</li>
 *   <li>{@link Assistant#getAge()}</li>
 *   <li>{@link Assistant#getSex()}</li>
 *   <li>{@link Assistant#getDescription()}</li>
 *   <li>{@link Assistant#getPersonality()}</li>
 *   <li>{@link Assistant#getMood()}</li>
 *   <li>{@link Assistant#getStyle()}</li>
 *   <li>{@link Assistant#getTemperature()}</li>
 *   <li>{@link Assistant#setEmotionalState(String)}</li>
 *   <li>{@link Assistant#getEmotionalState()}</li>
 *   <li>{@link Assistant#getOpinions()}</li>
 *   <li>{@link Assistant#getStyleOverrides()}</li>
 *   <li>{@link Assistant#toString()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>The {@code Assistant} class is used to model and manage assistant entities in the system. It includes detailed
 * configurations such as personality, mood, styles, and more. Instantiate this class to create a fully configured
 * assistant ready for AI-driven interactions.</p>
 *
 * @see Assistant.StyleOverride
 * @see Assistant.Personality
 * @see Assistant.Mood
 * @see Assistant.Opinion
 * @see Assistant.OverrideCondition
 */
public class Assistant implements SessionData {
    /**
     * A unique identifier for the Assistant instance.
     */
    private final String id;
    /**
     * The name of the Assistant.
     */
    private final String name;
    /**
     * The profession or role assigned to the Assistant.
     */
    private final String profession;
    /**
     * The age of the Assistant.
     */
    private final int age;
    /**
     * The biological or identified sex of the Assistant.
     */
    private final Sex sex;
    /**
     * A brief textual description of the Assistant.
     */
    private final String description;
    /**
     * Represents the Assistant's personality structure made up of multiple traits.
     */
    private final Personality personality;
    /**
     * An array of default communication and interaction styles used by the Assistant.
     */
    private final MetaDef[] style;
    /**
     * The temperature setting used by the Assistant to control randomness in generation.
     */
    private final double temperature;

    /**
     * The current emotional state label of the Assistant.
     */
    private String emotionalState;
    /**
     * The Assistant's current mood composed of multiple dynamic emotional metrics.
     */
    private final Mood mood;

    /**
     * A list of subjective opinions the Assistant holds about different {@link Player} entities.
     */
    private final List<Opinion> opinions;

    /**
     * A list of overrides that conditionally modify the Assistant's styles.
     */
    private final List<StyleOverride> styleOverrides;

    /**
     * Constructs an instance of the Assistant class.
     *
     * @param id {@link Assistant#id}
     * @param name {@link Assistant#name}
     * @param profession {@link Assistant#profession}
     * @param age {@link Assistant#age}
     * @param sex {@link Assistant#sex}
     * @param description  {@link Assistant#description}
     * @param personality {@link Assistant#personality}
     * @param style {@link Assistant#style}
     * @param emotionalState {@link Assistant#emotionalState}
     * @param mood {@link Assistant#mood}
     * @param opinions {@link Assistant#opinions}
     * @param temperature {@link Assistant#temperature}
     * @param styleOverrides {@link Assistant#styleOverrides}
     *
     * @see Assistant
     */
    public Assistant(@NotNull String id, @NotNull String name, @NotNull String profession, int age, @NotNull Sex sex, @NotNull String description, @NotNull Personality personality, @NotNull List<MetaDef> style, @NotNull String emotionalState, @NotNull Mood mood, @Nullable List<Opinion> opinions, @Nullable Double temperature, @Nullable List<StyleOverride> styleOverrides) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.age = age;
        this.sex = sex;
        this.description = description;
        this.personality = personality;
        this.style = style.toArray(new MetaDef[0]);
        this.temperature = temperature != null ? temperature : Main.getConfig().getDefaults().getTemperature();
        this.styleOverrides = styleOverrides != null ? styleOverrides : List.of();

        this.emotionalState = emotionalState;
        this.mood = mood;

        this.opinions = opinions != null ? opinions : List.of();
    }

    @Override
    public String toString() {
        return "Assistant{" +
                "id='" + this.id + "'" +
                ", name='" + this.name + "'" +
                ", profession='" + this.profession + "'" +
                ", age=" + this.age +
                ", sex=" + this.sex.name() +
                ", description='" + this.description + "'" +
                ", personality=" + this.personality +
                ", style=" + Arrays.toString(this.style) +
                ", temperature=" + this.temperature +
                ", styleOverrides=" + this.styleOverrides +
                ", emotionalState='" + this.emotionalState + "'" +
                ", mood='" + this.mood +
                ", opinions=" + this.opinions +
                "}";
    }

    public String getStyledData() {
        String YELLOW = "\u001B[33m";
        String GREEN = "\u001B[32m";
        String BLUE = "\u001B[34m";
        String RESET = "\u001B[0m";

        String personality = "personality: ";
        String personalityString = BLUE + "empathy: " + YELLOW + this.getPersonality().discipline.getScore() + RESET + "\n" +
                BLUE + " ".repeat(personality.length()) + "discipline: " + YELLOW + this.getPersonality().discipline.getScore() + RESET + "\n" +
                BLUE + " ".repeat(personality.length()) + "temper: " + YELLOW + this.getPersonality().temper.getScore() + RESET + "\n" +
                BLUE + " ".repeat(personality.length()) + "confidence: " + YELLOW + this.getPersonality().confidence.getScore() + RESET + "\n" +
                BLUE + " ".repeat(personality.length()) + "curiosity: " + YELLOW + this.getPersonality().curiosity.getScore() + RESET + "\n" +
                BLUE + " ".repeat(personality.length()) + "humor: " + YELLOW + this.getPersonality().humor.getScore() + RESET + "\n";

        String style;
        if (this.getStyleOverrides().isEmpty()) {
            style = "style: ";
        } else {
            style = "style (default): ";
        }
        StringBuilder styleSb = new StringBuilder();
        boolean isFirstStyle = true;
        for (MetaDef def : this.getStyle()) {
            styleSb.append(YELLOW).append(isFirstStyle ? "" : " ".repeat(style.length())).append(def.name()).append(RESET).append("\n");
            isFirstStyle = false;
        }

        StringBuilder overridesSb = new StringBuilder();
        for (StyleOverride o : this.getStyleOverrides()) {
            String condition = o.getCondition().condition + ": ";
            StringBuilder conditionStyleSb = new StringBuilder();
            boolean isFirstConditionStyle = true;
            for (MetaDef s : o.styles) {
                conditionStyleSb.append(YELLOW).append(isFirstConditionStyle ? "" : " ".repeat(condition.length())).append(s.name()).append(RESET).append("\n");
                isFirstConditionStyle = false;
            }

            overridesSb.append(GREEN).append(condition).append(conditionStyleSb).append(RESET);
        }

        String mood = "mood: ";
        String moodSb = BLUE + "socialEnergy: " + YELLOW + this.getMood().socialEnergy.getScore() + RESET + "\n" +
                BLUE + " ".repeat(mood.length()) + "trustInOthers: " + YELLOW + this.getMood().trustInOthers.getScore() + RESET + "\n" +
                BLUE + " ".repeat(mood.length()) + "emotionalStability: " + YELLOW + this.getMood().emotionalStability.getScore() + RESET + "\n" +
                BLUE + " ".repeat(mood.length()) + "motivation: " + YELLOW + this.getMood().motivation.getScore() + RESET + "\n" +
                BLUE + " ".repeat(mood.length()) + "pride: " + YELLOW + this.getMood().pride.getScore() + RESET + "\n" +
                BLUE + " ".repeat(mood.length()) + "attachmentToOthers: " + YELLOW + this.getMood().attachmentToOthers.getScore() + RESET + "\n";

        String opinions = "opinions: ";
        StringBuilder opinionsSb = new StringBuilder();
        boolean isFirstOpinion = true;
        boolean isLast = this.getOpinions().size() == 1;
        for (Opinion o : this.getOpinions()) {
            isLast = isLast || o == this.getOpinions().getLast();
            String playerName = o.player.getName();
            opinionsSb.append(GREEN).append(isFirstOpinion ? "" : " ".repeat(opinions.length())).append(playerName).append(": ").append(BLUE).append("trustworthiness: ").append(YELLOW).append(o.trustworthiness.getScore()).append(RESET).append("\n");
            opinionsSb.append(GREEN).append(" ".repeat(opinions.length() + playerName.length())).append(playerName).append(": ").append(BLUE).append("respect: ").append(YELLOW).append(o.respect.getScore()).append(RESET).append("\n");
            opinionsSb.append(GREEN).append(" ".repeat(opinions.length() + playerName.length())).append(playerName).append(": ").append(BLUE).append("likability: ").append(YELLOW).append(o.likability.getScore()).append(RESET).append("\n");
            opinionsSb.append(GREEN).append(" ".repeat(opinions.length() + playerName.length())).append(playerName).append(": ").append(BLUE).append("reliability: ").append(YELLOW).append(o.reliability.getScore()).append(RESET).append("\n");
            opinionsSb.append(GREEN).append(" ".repeat(opinions.length() + playerName.length())).append(playerName).append(": ").append(BLUE).append("emotionalBond: ").append(YELLOW).append(o.emotionalBond.getScore()).append(RESET).append(isLast ? "" : "\n");
            isFirstOpinion = false;
        }

        return BLUE + "id: " + YELLOW + this.getId() + RESET + "\n" +
                BLUE + "name: " + YELLOW + this.getName() + RESET + "\n" +
                BLUE + "profession: " + YELLOW + this.getProfession() + RESET + "\n" +
                BLUE + "age: " + YELLOW + this.getAge() + RESET + "\n" +
                BLUE + "sex: " + YELLOW + this.getSex().name() + RESET + "\n" +
                BLUE + "description: " + YELLOW + this.getDescription() + RESET + "\n" +
                GREEN + personality + personalityString + RESET +
                GREEN + style + styleSb + RESET +
                overridesSb +
                BLUE + "temperature: " + YELLOW + this.getTemperature() + RESET + "\n" + // TODO: add styleOverrides
                BLUE + "emotionalState: " + YELLOW + this.getEmotionalState() + RESET + "\n" +
                GREEN + mood + moodSb + RESET +
                GREEN + opinions + opinionsSb + RESET;
    }

    /**
     * Retrieves the unique identifier for this instance of Assistant.
     *
     * @return {@link Assistant#id}
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Retrieves the Assistant's name.
     *
     * @return {@link Assistant#name}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the profession assigned to the Assistant.
     *
     * @return {@link Assistant#profession}
     */
    @Override
    public String getProfession() {
        return this.profession;
    }

    /**
     * Retrieves the age of the Assistant.
     *
     * @return {@link Assistant#age}
     */
    @Override
    public int getAge() {
        return this.age;
    }

    /**
     * Retrieves the sex of the Assistant.
     *
     * @return {@link Assistant#sex}
     */
    @Override
    public Sex getSex() {
        return this.sex;
    }

    /**
     * Retrieves the Assistant's descriptive summary.
     *
     * @return {@link Assistant#description}
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieves the Assistant's personality traits.
     *
     * @return {@link Assistant#personality}
     */
    public Personality getPersonality() {
        return this.personality;
    }

    /**
     * Retrieves the default styles used by the Assistant.
     *
     * @return {@link Assistant#style}
     */
    public MetaDef[] getStyle() {
        return this.style;
    }

    /**
     * Retrieves the Assistant's temperature value.
     *
     * @return {@link Assistant#temperature}
     */
    public double getTemperature() {
        return this.temperature;
    }

    /**
     * Sets the emotional state of the Assistant.
     *
     * @param emotionalState The new {@link Assistant#emotionalState} value.
     * @return this {@link Assistant}
     */
    public Assistant setEmotionalState(String emotionalState) {
        this.emotionalState = emotionalState;
        return this;
    }

    /**
     * Retrieves the Assistant's current emotional state label.
     *
     * @return {@link Assistant#emotionalState}
     */
    public String getEmotionalState() {
        return emotionalState;
    }

    /**
     * Retrieves the Assistant's mood profile.
     *
     * @return {@link Assistant#mood}
     */
    public Mood getMood() {
        return mood;
    }

    /**
     * Retrieves the Assistant's opinions of various players.
     *
     * @return {@link Assistant#opinions}
     */
    public List<Opinion> getOpinions() {
        return opinions;
    }

    /**
     * Retrieves the list of style overrides applied to the Assistant.
     *
     * @return {@link Assistant#styleOverrides}
     */
    public List<StyleOverride> getStyleOverrides() {
        return new ArrayList<>(this.styleOverrides);
    }

    /**
     * <b>Assistant.StyleOverride Class</b> - Represents a conditional style override for the assistant.
     * <p>This class defines a set of styles and a condition under which those styles should override the assistant's
     * default styles.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link Assistant.StyleOverride#condition}</li>
     *   <li>{@link Assistant.StyleOverride#styles}</li>
     * </ul>
     * <b>Methods:</b>
     * <ul>
     *   <li>{@link Assistant.StyleOverride#getCondition()}</li>
     *   <li>{@link Assistant.StyleOverride#getStyles()}</li>
     *   <li>{@link Assistant.StyleOverride#toString()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Create an instance of {@code StyleOverride} to conditionally modify the assistant's styles based on specific
     * criteria or situations.</p>
     *
     * @see Assistant
     */
    public static class StyleOverride {
        /**
         * The condition under which this style override should be applied.
         */
        private final OverrideCondition condition;
        /**
         * The set of style definitions to use if the condition is met.
         */
        private final MetaDef[] styles;

        /**
         * Constructs a new {@link StyleOverride} instance using a specified condition and list of styles.
         *
         * @param condition The {@link OverrideCondition} that must be satisfied for this override to apply.
         * @param styles A list of {@link MetaDef} styles to override the assistant's default styles with.
         * @see StyleOverride
         */
        public StyleOverride(OverrideCondition condition, List<MetaDef> styles) {
            this.condition = condition;
            this.styles = styles.toArray(new MetaDef[0]);
        }

        /**
         * Retrieves the condition under which this style override should be triggered.
         *
         * @return {@link StyleOverride#condition}
         */
        public OverrideCondition getCondition() {
            return condition;
        }

        /**
         * Retrieves the styles to apply when the override condition is met.
         *
         * @return {@link StyleOverride#styles}
         */
        public MetaDef[] getStyles() {
            return styles;
        }

        @Override
        public String toString() {
            return "StyleOverride{" +
                    "condition='" + this.condition.condition + "'" +
                    ", styles=" + Arrays.toString(this.styles) +
                    '}';
        }
    }

    /**
     * <b>Assistant.Personality</b> - Represents the personality traits of the assistant.
     * <p>This record encapsulates six traits: empathy, discipline, temper, confidence, curiosity, and humor. Each
     * trait is defined as a {@link com.ethanrobins.ai_npc_concept.meta.MetaScore}.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@code empathy}</li>
     *   <li>{@code discipline}</li>
     *   <li>{@code temper}</li>
     *   <li>{@code confidence}</li>
     *   <li>{@code curiosity}</li>
     *   <li>{@code humor}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Use this record to encapsulate personality traits for creating or configuring assistants.</p>
     *
     * @param empathy The assistant’s capacity to emotionally understand and connect with others.
     * @param discipline The assistant’s ability to stay organized, goal-driven, and consistent.
     * @param temper The assistant’s impulsiveness or emotional volatility.
     * @param confidence The assistant’s level of self-assurance and certainty in actions.
     * @param curiosity The assistant’s desire to learn, explore, and investigate new things.
     * @param humor The assistant’s sense of playfulness and ability to find amusement.
     * @see Assistant
     */
    public record Personality(
            MetaScore empathy,
            MetaScore discipline,
            MetaScore temper,
            MetaScore confidence,
            MetaScore curiosity,
            MetaScore humor) {
    }

    /**
     * <b>Assistant.Mood</b> - Represents the current mood state of the assistant.
     * <p>This record captures six mood attributes: social energy, trust in others, emotional stability,
     * motivation, pride, and attachment to others. Each is represented as a {@link com.ethanrobins.ai_npc_concept.meta.MetaScore}.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@code socialEnergy}</li>
     *   <li>{@code trustInOthers}</li>
     *   <li>{@code emotionalStability}</li>
     *   <li>{@code motivation}</li>
     *   <li>{@code pride}</li>
     *   <li>{@code attachmentToOthers}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Use this record to define and manage the assistant's dynamic mood behaviors for more nuanced interactions.</p>
     *
     * @param socialEnergy Represents how energized or drained the assistant feels around others.
     * @param trustInOthers Reflects the assistant’s comfort with relying on other individuals.
     * @param emotionalStability Indicates how resilient or reactive the assistant is to stress or stimuli.
     * @param motivation Measures the assistant’s drive and focus toward tasks or goals.
     * @param pride Reflects the assistant’s sense of self-worth or ego in relation to others.
     * @param attachmentToOthers Measures the assistant’s need for closeness and emotional bonding.
     * @see Assistant
     */
    public record Mood(
            MetaScore socialEnergy,
            MetaScore trustInOthers,
            MetaScore emotionalStability,
            MetaScore motivation,
            MetaScore pride,
            MetaScore attachmentToOthers
    ) {}

    /**
     * <b>Assistant.Opinion</b> - Represents an assistant's subjective view of a player.
     * <p>This record encapsulates the assistant's perception of a player, measured across multiple dimensions such as
     * trustworthiness, likability, respect, and more. Each measurement is represented as a {@link com.ethanrobins.ai_npc_concept.meta.MetaScore}.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@code player}</li>
     *   <li>{@code trustworthiness}</li>
     *   <li>{@code respect}</li>
     *   <li>{@code likability}</li>
     *   <li>{@code reliability}</li>
     *   <li>{@code emotionalBond}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Create an instance of {@code Opinion} to store or retrieve the assistant's subjective view of a specific player.
     * These opinions can influence how the assistant interacts with different players.</p>
     *
     * @param player The player this opinion is associated with.
     * @param trustworthiness How dependable or honest the assistant believes the player is.
     * @param respect The assistant’s regard for the player’s ability, status, or conduct.
     * @param likability How friendly or agreeable the assistant finds the player.
     * @param reliability How much the assistant believes the player will follow through on actions.
     * @param emotionalBond The emotional connection or closeness the assistant feels toward the player.
     * @see Assistant
     */
    public record Opinion(
            Player player,
            MetaScore trustworthiness,
            MetaScore respect,
            MetaScore likability,
            MetaScore reliability,
            MetaScore emotionalBond
    ) {}

    /**
     * <b>Assistant.OverrideCondition</b> - Specifies the conditions under which assistant styles will be overridden.
     * <p>This enum defines symbolic conditions for activating {@link Assistant.StyleOverride}s based on runtime context.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link Assistant.OverrideCondition#condition}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link Assistant.OverrideCondition#getCondition()}</li>
     *   <li>{@link Assistant.OverrideCondition#fromString(String)}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Use {@code OverrideCondition} to dynamically trigger style changes for assistants when certain criteria are met.</p>
     *
     * @see Assistant.StyleOverride
     */
    public enum OverrideCondition {
        /**
         * Indicates that the assistant should override its style when its emotional bond with the player is high.
         */
        EMOTIONAL_BOND_HIGH("emotional_bond_high");

        /**
         * The string identifier representing this override condition.
         */
        private final String condition;

        /**
         * Constructs an {@link OverrideCondition} enum with a condition string.
         *
         * @param condition {@link OverrideCondition#condition}
         * @see OverrideCondition
         */
        OverrideCondition(String condition) {
            this.condition = condition;
        }

        /**
         * Returns the string representation of this {@link OverrideCondition}.
         *
         * @return {@link OverrideCondition#condition}
         */
        public String getCondition() {
            return condition;
        }

        /**
         * Converts a string into its corresponding {@link OverrideCondition} enum.
         *
         * @param condition The condition string to parse.
         * @return The matching {@link OverrideCondition}.
         * @throws IllegalArgumentException If no matching condition is found.
         */
        public static OverrideCondition fromString(String condition) {
            for (OverrideCondition overrideCondition : OverrideCondition.values()) {
                if (overrideCondition.condition.equals(condition)) {
                    return overrideCondition;
                }
            }

            throw new IllegalArgumentException("Invalid OverrideCondition: " + condition);
        }
    }
}
