package com.ethanrobins.ai_npc_concept;

import com.ethanrobins.ai_npc_concept.meta.MetaDef;
import com.ethanrobins.ai_npc_concept.meta.MetaScore;
import com.ethanrobins.ai_npc_concept.meta.Sex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assistant implements SessionData {
    private final String id;
    private final String name;
    private final String profession;
    private final int age;
    private final Sex sex;
    private final String description;
    private final Personality personality;
    private final MetaDef[] style;
    private final double temperature;

    private String emotionalState;
    private final Mood mood;

    private final List<Opinion> opinions;

    private final List<StyleOverride> styleOverrides;

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
                "id: '" + this.id + "'" +
                ", name: '" + this.name + "'" +
                ", profession: '" + this.profession + "'" +
                ", age: " + this.age +
                ", sex: " + this.sex.name() +
                ", description: '" + this.description + "'" +
                ", personality: " + this.personality +
                ", style: " + Arrays.toString(this.style) +
                ", temperature: " + this.temperature +
                ", styleOverrides: " + this.styleOverrides +
                ", emotionalState: '" + this.emotionalState + "'" +
                ", mood: '" + this.mood +
                ", opinions: " + this.opinions +
                "}";
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getProfession() {
        return this.profession;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public Sex getSex() {
        return this.sex;
    }

    public String getDescription() {
        return this.description;
    }

    public Personality getPersonality() {
        return this.personality;
    }

    public MetaDef[] getStyle() {
        return this.style;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public Assistant setEmotionalState(String emotionalState) {
        this.emotionalState = emotionalState;
        return this;
    }

    public String getEmotionalState() {
        return emotionalState;
    }

    public Mood getMood() {
        return mood;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public List<StyleOverride> getStyleOverrides() {
        return new ArrayList<>(this.styleOverrides);
    }

    public static class StyleOverride {
        private final OverrideCondition condition;
        private final MetaDef[] styles;

        public StyleOverride(OverrideCondition condition, List<MetaDef> styles) {
            this.condition = condition;
            this.styles = styles.toArray(new MetaDef[0]);
        }

        public OverrideCondition getCondition() {
            return condition;
        }

        public MetaDef[] getStyles() {
            return styles;
        }

        @Override
        public String toString() {
            return "StyleOverride{" +
                    "condition: '" + this.condition.condition + "'" +
                    ", styles: " + Arrays.toString(this.styles) +
                    '}';
        }
    }

    /**
     * @param empathy
     * @param discipline
     * @param temper
     * @param confidence
     * @param curiosity
     * @param humor
     */
    public record Personality(
            MetaScore empathy,
            MetaScore discipline,
            MetaScore temper,
            MetaScore confidence,
            MetaScore curiosity,
            MetaScore humor
    ) {}

    /**
     * @param socialEnergy
     * @param trustInOthers
     * @param emotionalStability
     * @param motivation
     * @param pride
     * @param attachmentToOthers
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
     * @param player
     * @param trustworthiness
     * @param respect
     * @param likability
     * @param reliability
     * @param emotionalBond
     */
    public record Opinion(
            Player player,
            MetaScore trustworthiness,
            MetaScore respect,
            MetaScore likability,
            MetaScore reliability,
            MetaScore emotionalBond
    ) {}

    public enum OverrideCondition {
        EMOTIONAL_BOND_HIGH("emotional_bond_high");

        private final String condition;

        OverrideCondition(String condition) {
            this.condition = condition;
        }

        public String getCondition() {
            return condition;
        }

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
