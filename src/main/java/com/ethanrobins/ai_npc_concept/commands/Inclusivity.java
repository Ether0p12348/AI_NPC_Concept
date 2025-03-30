package com.ethanrobins.ai_npc_concept.commands;

/**
 * <b>Inclusivity</b> - Represents inclusion or exclusion logic used to filter or interpret commands, contexts, or conditions.
 * <p>This enum defines five distinct levels of inclusivity which may be used in decision trees, configuration
 * systems, or command processing to evaluate what entities or elements are permitted, required, or restricted.</p>
 *
 * <br><b>Constants:</b>
 * <ul>
 *   <li>{@link Inclusivity#INCLUSIVE}</li>
 *   <li>{@link Inclusivity#EXCLUSIVE}</li>
 *   <li>{@link Inclusivity#REQUIRED_INCLUSIVE}</li>
 *   <li>{@link Inclusivity#REQUIRED_EXCLUSIVE}</li>
 *   <li>{@link Inclusivity#RESTRICTED}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Apply this enum to determine whether certain elements should be accepted, denied, or explicitly required
 * or excluded from a set, especially in filtering or rule evaluation scenarios.</p>
 */
public enum Inclusivity {
    /**
     * Indicates that any number or zero of the tags within the set can be used per execution.
     */
    INCLUSIVE,
    /**
     * Indicates that only one or zero of the tags within the set can be used per execution.
     */
    EXCLUSIVE,
    /**
     * Indicates that any number but at least one of the tags within the set must be used per execution.
     */
    REQUIRED_INCLUSIVE,
    /**
     * Indicates that only one, no more, no less of the tags within the set must be used per execution.
     */
    REQUIRED_EXCLUSIVE,
    /**
     * Indicates that none of the tags within the set may be used.
     */
    RESTRICTED
}
