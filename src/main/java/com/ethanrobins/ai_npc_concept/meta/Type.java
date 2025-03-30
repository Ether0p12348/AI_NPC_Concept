package com.ethanrobins.ai_npc_concept.meta;

/**
 * <b>Type Enum</b> - Represents high-level metadata categories used to define attributes of assistants or players.
 * <p>This enum classifies types of traits and definitions that describe how an entity behaves, thinks, or interacts.
 * It serves as a key for retrieving corresponding {@link com.ethanrobins.ai_npc_concept.configs.Defs} from configuration.</p>
 *
 * <br><b>Enum Constants:</b>
 * <ul>
 *   <li>{@link Type#MOOD}</li>
 *   <li>{@link Type#OPINION}</li>
 *   <li>{@link Type#PERSONALITY}</li>
 *   <li>{@link Type#STYLE}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Use {@code Type} values to reference groups of definitions in configuration and runtime logic. For example,
 * selecting {@link Type#MOOD} will access {@code MoodDefs} and related mood-specific metadata.</p>
 */
public enum Type {
    /**
     * Defines dynamic mood attributes such as emotional stability, motivation, or social energy.
     */
    MOOD,
    /**
     * Represents how an assistant perceives a player, including trust, likability, and respect.
     */
    OPINION,
    /**
     * Represents inherent personality traits that shape long-term behavioral patterns.
     */
    PERSONALITY,
    /**
     * Represents communicative or expressive styles such as tone, formality, or vocabulary.
     */
    STYLE
}
