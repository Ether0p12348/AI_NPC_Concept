package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>PersonalityDefs</b> - A container class for storing personality trait definitions.
 * <p>Extends {@link Defs} and manages a collection of {@link PersonalityDefs.Personality} objects,
 * which represent character traits such as empathy, discipline, or curiosity. These traits include
 * score-based descriptors for behavioral interpretation.</p>
 *
 * <b>Usage:</b>
 * <p>Used to define and describe personality traits for assistants or NPCs based on score interpretation ranges.</p>
 *
 * @see PersonalityDefs.Personality
 */
public class PersonalityDefs extends Defs<PersonalityDefs.Personality> {
    /**
     * Constructs a {@link PersonalityDefs} object from JSON.
     *
     * @param personalityMap {@link #defs}
     */
    @JsonCreator
    public PersonalityDefs(Map<String, Personality> personalityMap) {
        super(() -> {
            List<Personality> l = new ArrayList<>();
            personalityMap.forEach((k, v) -> l.add(new Personality(k, v.id, v.name, v.description, v.scores)));

            return l.toArray(new Personality[0]);
        });
    }

    /**
     * <b>PersonalityDefs.Personality</b> - Represents a single personality trait with interpretation scores.
     * <p>Extends {@link Defs.Def} and adds a {@link Defs.Def.Scores} object that defines descriptive interpretations
     * for low, neutral, and high values of this trait.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link #scores} - Holds interpretations for score ranges.</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getScores()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Used to define traits such as empathy, humor, or confidence, with varying descriptions depending on their score.</p>
     *
     * @see PersonalityDefs
     * @see Defs.Def.Scores
     */
    public static class Personality extends Defs.Def {
        /**
         * Score-based descriptors for this personality trait (low, neutral, high).
         */
        private final Defs.Def.Scores scores;

        /**
         * Constructs a {@link Personality} object from JSON.
         *
         * @param identifier {@link #identifier}
         * @param id {@link #id}
         * @param name {@link #name}
         * @param description {@link #description}
         * @param scores {@link #scores}
         * @throws NullPointerException if {@link #id} or {@link #name} is {@code null}.
         */
        @JsonCreator
        public Personality(@JsonProperty("identifier") @Nullable String identifier, @JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("description") @Nullable String description, @JsonProperty("scores") @Nullable Scores scores) throws NullPointerException {
            super(identifier, id, name, description);
            this.scores = scores != null ? scores : new Scores("low", "neutral", "high");
        }

        /**
         * Retrieves the score interpretation values for this personality.
         *
         * @return {@link #scores}
         */
        public Defs.Def.Scores getScores() {
            return scores;
        }

        @Override
        public String toString() {
            return "Personality{" +
                    "identifier='" + identifier + '\'' +
                    ", id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", scores=" + scores +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PersonalityDefs{" +
                "personalities=" + defs +
                '}';
    }
}
