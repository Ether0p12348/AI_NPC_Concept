package com.ethanrobins.ai_npc_concept;

import com.ethanrobins.ai_npc_concept.meta.Sex;

/**
 * <b>SessionData Interface</b> - Represents the core data structure for session-related information in the application.
 * <p>This interface defines the methods required to retrieve essential data about a session, such as the
 * entity's ID, name, profession, age, and sex.</p>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link SessionData#getId()}</li>
 *   <li>{@link SessionData#getName()}</li>
 *   <li>{@link SessionData#getProfession()}</li>
 *   <li>{@link SessionData#getAge()}</li>
 *   <li>{@link SessionData#getSex()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Implement this interface to provide concrete representations of session-related data objects that store
 * or retrieve information for various entities participating in a session.</p>
 */
public interface SessionData {
    /**
     * Retrieves the unique identifier associated with this session entity.
     *
     * @return The unique ID of the entity.
     */
    String getId();

    /**
     * Retrieves the display name of the session entity.
     *
     * @return The name of the entity.
     */
    String getName();

    /**
     * Retrieves the profession or role of the session entity.
     *
     * @return The profession of the entity.
     */
    String getProfession();

    /**
     * Retrieves the age of the session entity.
     *
     * @return The age of the entity.
     */
    int getAge();

    /**
     * Retrieves the biological or identified sex of the session entity.
     *
     * @return The {@link Sex} of the entity.
     */
    Sex getSex();
}
