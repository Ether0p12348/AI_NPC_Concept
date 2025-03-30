package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>MoodDefs</b> - A concrete implementation of {@link Defs} that stores a list of mood-related metadata definitions.
 * <p>This class is used to define specific mood traits such as happiness, anxiety, trust, etc. Each mood contains
 * optional text descriptions for different score ranges (low, neutral, high), making them more interpretable in UI or logs.</p>
 *
 * <br><b>Usage:</b>
 * <p>Used by the application to load and manage mood-based traits from {@code tags/mood.json}.</p>
 *
 * @see MoodDefs.Mood
 */
public class MoodDefs extends Defs<MoodDefs.Mood> {

    /**
     * Constructs a {@link MoodDefs} object from JSON.
     *
     * @param moodMap {@link #defs}
     */
    @JsonCreator
    public MoodDefs(Map<String, Mood> moodMap) {
        super(() -> {
            List<Mood> l = new ArrayList<>();
            moodMap.forEach((k, v) -> l.add(new Mood(k, v.id, v.name, v.description, v.scores)));

            return l.toArray(new Mood[0]);
        });
    }

    /**
     * <b>MoodDefs.Mood</b> - Represents a single mood definition within {@link MoodDefs}.
     * <p>Extends {@link Defs.Def} to include additional interpretation labels based on score thresholds
     * through the embedded {@link Scores} class.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link #scores}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getScores()}</li>
     * </ul>
     *
     * @see MoodDefs
     * @see Scores
     */
    public static class Mood extends Defs.Def{
        /**
         * Interpretation strings for different score ranges associated with this mood.
         */
        private final Defs.Def.Scores scores;

        /**
         * Constructs a {@link Def} object from JSON.
         *
         * @param identifier {@link #identifier}
         * @param id {@link #id}
         * @param name {@link #name}
         * @param description {@link #description}
         * @param scores {@link #scores}
         * @throws NullPointerException if {@link #id} or {@link #name} is {@code null}.
         */
        @JsonCreator
        public Mood(@JsonProperty("identifier") @Nullable String identifier, @JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("description") @Nullable String description, @JsonProperty("scores") @Nullable Scores scores) throws NullPointerException {
            super(identifier, id, name, description);
            this.scores = scores != null ? scores : new Scores("low", "neutral", "high");
        }

        /**
         * Retrieves the {@link Scores} object that describes how this mood should be interpreted.
         *
         * @return {@link MoodDefs.Mood#scores}
         */
        public Defs.Def.Scores getScores() {
            return scores;
        }

        @Override
        public String toString() {
            return "Mood{" +
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
        return "MoodDefs{" +
                "moods=" + defs +
                '}';
    }
}
