package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * <b>Defs Class</b> - Abstract base class for managing collections of metadata definitions.
 * <p>This generic class is extended by specific configuration holders such as {@code MoodDefs}, {@code OpinionDefs},
 * and others. It provides utilities for retrieving definitions by ID or identifier.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link #defs}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link #getDefs()}</li>
 *   <li>{@link #getDef(String)}</li>
 *   <li>{@link #getDefByIdentifier(String)}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Extend this class to implement domain-specific metadata containers that can be serialized and deserialized
 * using Jackson and programmatically queried at runtime.</p>
 *
 * @param <T> A class extending {@link Defs.Def}, representing individual metadata definitions.
 */
public abstract class Defs<T extends Defs.Def> {
    /**
     * The list of metadata definitions managed by this configuration class.
     */
    protected final T[] defs;

    /**
     * <b>WARNING:</b> Never deserialize with {@link Defs} directly. Only with extended classes.
     * <br>Constructs a {@link Defs} object from supplier.
     *
     * <br><br><b>Example Constructor:</b>
     * <pre>{@code @JsonCreator
     * public MoodDefs(Map<String, Mood> moodMap) {
     *     super(() -> {
     *         List<Mood> l = new ArrayList<>();
     *         moodMap.forEach((k, v) -> l.add(new Mood(k, v.id, v.name, v.description, v.scores)));
     *
     *         return l.toArray(new Mood[0]);
     *     });
     * }}</pre> <i>from {@link MoodDefs}.</i>
     *
     * @param defsSupplier returns {@link #defs}
     */
    @JsonIgnore
    public Defs(Supplier<T[]> defsSupplier) {
        this.defs = defsSupplier.get();
    }

    /**
     * Retrieves the list of all metadata definitions.
     *
     * @return {@link #defs}
     */
    public List<T> getDefs() {
        return Arrays.stream(defs).toList();
    }

    /**
     * Retrieves a definition by its internal ID (case-insensitive).
     *
     * @param id The ID to search for.
     * @return The matching definition or {@code null} if not found.
     */
    @Nullable
    @JsonIgnore
    public T getDef(String id) {
        for (T def : defs) {
            if (def.getId().equalsIgnoreCase(id)) {
                return def;
            }
        }
        return null;
    }

    /**
     * Retrieves a definition by its external identifier (case-insensitive).
     *
     * @param identifier The identifier to search for.
     * @return The matching definition or {@code null} if not found.
     */
    @Nullable
    @JsonIgnore
    public T getDefByIdentifier(String identifier) {
        for (T def : defs) {
            if (def.getIdentifier().equalsIgnoreCase(identifier)) {
                return def;
            }
        }
        return null;
    }

    /**
     * <b>Defs.Def</b> - Represents a single metadata definition.
     * <p>This abstract base class holds common fields like {@code identifier}, {@code id}, {@code name}, and
     * {@code description} shared across all metadata types (mood, opinion, personality, style).</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link #identifier}</li>
     *   <li>{@link #id}</li>
     *   <li>{@link #name}</li>
     *   <li>{@link #description}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getIdentifier()}</li>
     *   <li>{@link #getId()}</li>
     *   <li>{@link #getName()}</li>
     *   <li>{@link #getDescription()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Extend this class to create specific types of definitions used in assistant and player configuration models.</p>
     */
    public static abstract class Def {
        /**
         * A secondary identifier used to uniquely identify the definition from configuration.
         */
        protected final String identifier;
        /**
         * A short string ID used internally to reference the definition.
         */
        protected final String id;
        /**
         * The display name of the definition.
         */
        protected final String name;
        /**
         * A textual explanation of what this definition represents.
         */
        protected final String description;

        /**
         * Constructs a {@link Def} object from JSON.
         *
         * @param identifier {@link #identifier}
         * @param id {@link #id}
         * @param name {@link #name}
         * @param description {@link #description}
         * @throws NullPointerException if {@link #id} or {@link #name} is {@code null}.
         */
        @JsonCreator
        public Def(@JsonProperty("identifier") @Nullable String identifier, @JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("description") @Nullable String description) throws NullPointerException {
            if (id == null) {
                throw new NullPointerException("Def ID cannot be null.");
            } else if (name == null) {
                throw new NullPointerException("Def name cannot be null.");
            }

            this.identifier = identifier != null ? identifier : "id";
            this.id = id;
            this.name = name;
            this.description = description != null ? description : "";
        }

        /**
         * Gets the external identifier for this definition.
         *
         * @return {@link #identifier}
         */
        public String getIdentifier() {
            return identifier;
        }

        /**
         * Gets the internal ID for this definition.
         *
         * @return {@link #id}
         */
        public String getId() {
            return id;
        }

        /**
         * Gets the display name for this definition.
         *
         * @return {@link #name}
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the textual description of this definition.
         *
         * @return {@link #description}
         */
        public String getDescription() {
            return description;
        }

        /**
         * <b>Defs.Def.Scores</b> - Represents score-range labels for a single Def.
         * <p>Contains descriptions for low, neutral, and high values typically used in NPC opinion scoring.</p>
         *
         * <br><b>Fields:</b>
         * <ul>
         *   <li>{@link #low}</li>
         *   <li>{@link #neutral}</li>
         *   <li>{@link #high}</li>
         * </ul>
         *
         * <br><b>Methods:</b>
         * <ul>
         *   <li>{@link #getLow()}</li>
         *   <li>{@link #getNeutral()}</li>
         *   <li>{@link #getHigh()}</li>
         * </ul>
         *
         * <b>Usage:</b>
         * <p>Used to translate raw numeric Def scores into human-readable insights.</p>
         */
        public static class Scores {
            /**
             * Description of a low score (e.g., "distrust").
             */
            private final String low;
            /**
             * Description of a neutral score (e.g., "uncertain").
             */
            private final String neutral;
            /**
             * Description of a high score (e.g., "highly favorable").
             */
            private final String high;

            /**
             * Constructs a {@link Scores} object from JSON.
             *
             * @param low {@link #low}
             * @param neutral {@link #neutral}
             * @param high {@link #high}
             */
            @JsonCreator
            public Scores(@JsonProperty("low") @Nullable String low, @JsonProperty("neutral") @Nullable String neutral, @JsonProperty("high") @Nullable String high) {
                this.low = low != null ? low : "low";
                this.neutral = neutral != null ? neutral : "neutral";
                this.high = high != null ? high : "high";
            }

            /**
             * Retrieves the description for low scores.
             *
             * @return {@link #low}
             */
            public String getLow() {
                return low;
            }

            /**
             * Retrieves the description for neutral scores.
             *
             * @return {@link #neutral}
             */
            public String getNeutral() {
                return neutral;
            }

            /**
             * Retrieves the description for high scores.
             *
             * @return {@link #high}
             */
            public String getHigh() {
                return high;
            }

            @Override
            public String toString() {
                return "Scores{" +
                        "low='" + low + '\'' +
                        ", medium='" + neutral + '\'' +
                        ", high='" + high + '\'' +
                        '}';
            }
        }

        /**
         * <b>Example:</b>
         * <pre>{@code @Override
         * public String toString() {
         *     return  + "Def{" +
         *             "identifier='" + identifier + '\'' +
         *             ", id='" + id + '\'' +
         *             ", name='" + name + '\'' +
         *             ", description='" + description + '\'' +
         *             '}';
         * }}</pre>
         */
        @Override
        public abstract String toString();
    }

    /**
     * <b>Example:</b>
     * <pre>{@code @Override
     * public String toString() {
     *     return "Defs{" +
     *             "styles=" + defs +
     *             '}';
     * }}</pre>
     */
    @Override
    public abstract String toString();
}
