package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * <b>OpinionDefs</b> - A container for managing opinion metadata definitions.
 * <p>Extends {@link Defs} to provide a registry of {@link OpinionDefs.Opinion} objects used to evaluate NPCs'
 * subjective views of players or other entities. Each opinion includes a score-based interpretation system
 * via {@link OpinionDefs.Opinion.Scores}.</p>
 *
 * <b>Usage:</b>
 * <p>Used to load structured opinion traits such as trust, respect, or likability from {@code tags/opinion.json}.</p>
 *
 * @see OpinionDefs.Opinion
 */
public class OpinionDefs extends Defs<OpinionDefs.Opinion> {
    /**
     * Constructs a {@link OpinionDefs} object from JSON.
     *
     * @param opinionMap {@link #defs}
     */
    @JsonCreator
    public OpinionDefs(Map<String, Opinion> opinionMap) {
        super(() -> {
            List<Opinion> l = new ArrayList<>();
            opinionMap.forEach((k, v) -> l.add(new Opinion(k, v.id, v.name, v.description, v.scores)));

            return l.toArray(new Opinion[0]);
        });
    }

    /**
     * <b>OpinionDefs.Opinion</b> - Represents a single opinion definition with score-based descriptors.
     * <p>Extends {@link Defs.Def} and adds a {@link Scores} field used to interpret opinion values as human-readable labels.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link #scores} - Stores text for low, neutral, and high opinion score interpretations.</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getScores()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Create instances of this class to describe how NPCs view other characters across multiple relationship dimensions.</p>
     * @see OpinionDefs
     * @see Scores
     */
    public static class Opinion extends Defs.Def {
        /**
         * The score-based interpretation strings for this opinion.
         */
        private final Defs.Def.Scores scores;

        /**
         * Constructs a {@link Opinion} object from JSON.
         *
         * @param identifier {@link #identifier}
         * @param id {@link #id}
         * @param name {@link #name}
         * @param description {@link #description}
         * @param scores {@link #scores}
         * @throws NullPointerException if {@link #id} or {@link #name} is {@code null}.
         */
        @JsonCreator
        public Opinion(@JsonProperty("identifier") @Nullable String identifier, @JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("description") @Nullable String description, @JsonProperty("scores") @Nullable Scores scores) throws NullPointerException {
            super(identifier, id, name, description);
            this.scores = scores != null ? scores : new Scores("low", "neutral", "high");
        }

        /**
         * Retrieves the score-based descriptions for this opinion.
         *
         * @return {@link OpinionDefs.Opinion#scores}
         */
        public Defs.Def.Scores getScores() {
            return scores;
        }

        @Override
        public String toString() {
            return "Opinion{" +
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
        return "OpinionDefs{" +
                "opinions=" + defs +
                '}';
    }
}
