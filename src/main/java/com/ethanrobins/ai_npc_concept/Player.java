package com.ethanrobins.ai_npc_concept;

import com.ethanrobins.ai_npc_concept.meta.Sex;
import org.jetbrains.annotations.NotNull;

/**
 * <b>Player Class</b> - A concrete implementation of {@link SessionData} representing a player entity.
 * <p>This class encapsulates a player's core attributes such as ID, name, profession, age, and sex. These
 * attributes are typically used for session identification, personalization, and AI interaction purposes.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link Player#id}</li>
 *   <li>{@link Player#name}</li>
 *   <li>{@link Player#profession}</li>
 *   <li>{@link Player#age}</li>
 *   <li>{@link Player#sex}</li>
 * </ul>
 *
 * <b>Methods:</b>
 * <ul>
 *   <li>{@link Player#getId()}</li>
 *   <li>{@link Player#getName()}</li>
 *   <li>{@link Player#getProfession()}</li>
 *   <li>{@link Player#getAge()}</li>
 *   <li>{@link Player#getSex()}</li>
 *   <li>{@link Player#toString()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Instantiate this class to represent a player involved in an AI-NPC interaction session.
 * This data may be used to influence NPC behavior, personalization, and long-term state tracking.</p>
 */

public class Player implements SessionData{
    /**
     * A unique identifier for the Player instance.
     */
    private final String id;
    /**
     * The name of the player.
     */
    private final String name;
    /**
     * The profession or role assigned to the player.
     */
    private final String profession;
    /**
     * The age of the player.
     */
    private final int age;
    /**
     * The biological or identified sex of the player.
     */
    private final Sex sex;

    /**
     * Constructs a {@link Player} instance with the given identity and demographic information.
     *
     * @param id {@link Player#id} - The unique identifier for the player.
     * @param name {@link Player#name} - The player's name.
     * @param profession {@link Player#profession} - The player's profession or role.
     * @param age {@link Player#age} - The player's age.
     * @param sex {@link Player#sex} - The player's biological or identified sex.
     */

    public Player(@NotNull String id, @NotNull String name, @NotNull String profession, int age, @NotNull Sex sex) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.age = age;
        this.sex = sex;
    }

    /**
     * Retrieves the unique identifier for this {@link Player}.
     *
     * @return {@link Player#id}
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return {@link Player#name}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the player's profession or in-game role.
     *
     * @return {@link Player#profession}
     */
    @Override
    public String getProfession() {
        return this.profession;
    }

    /**
     * Retrieves the player's age.
     *
     * @return {@link Player#age}
     */
    @Override
    public int getAge() {
        return this.age;
    }

    /**
     * Retrieves the biological or identified sex of the player.
     *
     * @return {@link Player#sex}
     */
    @Override
    public Sex getSex() {
        return this.sex;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id: '" + this.id + "'" +
                ", name: '" + this.name + "'" +
                ", profession: '" + this.profession + "'" +
                ", age: " + this.age +
                ", sex: " + this.sex.name() +
                "}";
    }
}
