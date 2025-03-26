package com.ethanrobins.ai_npc_concept;

import com.ethanrobins.ai_npc_concept.meta.MetaDef;
import com.ethanrobins.ai_npc_concept.meta.MetaScore;
import com.ethanrobins.ai_npc_concept.meta.Sex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Assistant {
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

    public Assistant(@NotNull String id, @NotNull String name, @NotNull String profession, int age, @NotNull Sex sex, @NotNull String description, @NotNull Personality personality, @NotNull List<MetaDef> style, @NotNull String emotionalState, @NotNull Mood mood, @Nullable List<Opinion> opinions, @Nullable Double temperature) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.age = age;
        this.sex = sex;
        this.description = description;
        this.personality = personality;
        this.style = style.toArray(new MetaDef[0]);
        this.temperature = temperature != null ? temperature : Main.getConfig().getDefaults().getTemperature();

        this.emotionalState = emotionalState;
        this.mood = mood;

        this.opinions = opinions != null ? opinions : List.of();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getProfession() {
        return this.profession;
    }

    public int getAge() {
        return this.age;
    }

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
}
