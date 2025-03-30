package com.ethanrobins.ai_npc_concept.meta;

/**
 * <b>MetaScore</b> - Represents a scored value tied to a specific {@link MetaDef} metadata definition.
 * <p>This class is used to assign a numeric score (e.g., from 1–10) to a metadata state such as mood, personality,
 * style, or opinion. It is useful in systems where AI behavior or relationships depend on dynamically scored attributes.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link MetaScore#state} - The metadata definition associated with this score.</li>
 *   <li>{@link MetaScore#score} - A numerical value (default 5) representing the current intensity, alignment, or state.</li>
 * </ul>
 *
 * <br><b>Constructors:</b>
 * <ul>
 *   <li>{@link MetaScore#MetaScore(MetaDef, int)}</li>
 *   <li>{@link MetaScore#MetaScore(MetaDef)}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link MetaScore#setScore(int)}</li>
 *   <li>{@link MetaScore#getScore()}</li>
 *   <li>{@link MetaScore#getState()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Create an instance of {@code MetaScore} to track how strongly an entity is associated with a specific trait or
 * behavior. Modify the score at runtime to reflect changes in behavior, relationships, or emotional state.</p>
 */
public class MetaScore {
    /**
     * The metadata definition this score is tied to (e.g., "anger", "curiosity").
     */
    private final MetaDef state;
    /**
     * The score value associated with the state, typically ranging from 0 (low) to 9 (high).
     * <ul>
     *     <li><b>0-3</b>: Low</li>
     *     <li><b>4-6</b>: Neutral</li>
     *     <li><b>7-9</b>: High</li>
     * </ul>
     */
    private int score;

    /**
     * Constructs a {@link MetaScore} with a specified state and score.
     *
     * @param state {@link MetaScore#state} - The metadata definition being scored.
     * @param score {@link MetaScore#score} - The score assigned to the definition.
     * @see MetaScore
     */
    public MetaScore(MetaDef state, int score) {
        this.state = state;
        this.score = score;
    }

    /**
     * Constructs a {@link MetaScore} with a specified state and a default score of 5 (neutral).
     *
     * @param state {@link MetaScore#state} - The metadata definition being scored.
     * @see MetaScore
     */
    public MetaScore(MetaDef state) {
        this.state = state;
        this.score = 5; // NEUTRAL
    }

    /**
     * Sets a new score value for the associated state.
     *
     * @param score The new score to assign.
     * @return this {@link MetaScore}
     */
    public MetaScore setScore(int score) {
        this.score = score;
        return this;
    }

    /**
     * Retrieves the metadata definition associated with this score.
     *
     * @return {@link MetaScore#state}
     */
    public MetaDef getState() {
        return this.state;
    }

    /**
     * Retrieves the current score value assigned to this metadata state.
     *
     * @return {@link MetaScore#score}
     */
    public int getScore() {
        return this.score;
    }
}
