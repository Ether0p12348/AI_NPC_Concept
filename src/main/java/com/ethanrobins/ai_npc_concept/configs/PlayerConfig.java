package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>PlayerConfig</b> - Represents a collection of player data loaded from configuration.
 * <p>This class is deserialized from {@code players.json} and stores a list of {@link PlayerConfig.Player}
 * entries that define the basic identity and metadata for each player in the system.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link #players} - The list of configured players.</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link #getPlayers()}</li>
 * </ul>
 *
 * @see PlayerConfig.Player
 */
public class PlayerConfig {
    /**
     * List of players defined in the configuration.
     */
    private final List<Player> players;

    @JsonCreator
    public PlayerConfig(@JsonProperty("players") @Nullable List<Player> players) {
        this.players = players != null ? players : new ArrayList<>();
    }

    /**
     * Retrieves the list of players from configuration.
     *
     * @return {@link #players}
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * <b>PlayerConfig.Player</b> - Represents a single player's identity and attributes loaded from configuration.
     * <p>Used to predefine player metadata such as name, profession, age, and sex.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link #id}</li>
     *   <li>{@link #name}</li>
     *   <li>{@link #profession}</li>
     *   <li>{@link #age}</li>
     *   <li>{@link #sex}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getId()}</li>
     *   <li>{@link #getName()}</li>
     *   <li>{@link #getProfession()}</li>
     *   <li>{@link #getAge()}</li>
     *   <li>{@link #getSex()}</li>
     * </ul>
     *
     * @see PlayerConfig
     */
    public static class Player {
        /**
         * The unique identifier for the player.
         */
        private final String id;
        /**
         * The player's display name.
         */
        private final String name;
        /**
         * The player's chosen profession or role.
         */
        private final String profession;
        /**
         * The age of the player.
         */
        private final int age;
        /**
         * The sex of the player (as a string; may be parsed into an enum at runtime).
         */
        private final String sex;

        /**
         * Constructs a {@link Player} object from JSON.
         *
         * @param id {@link #id}
         * @param name {@link #name}
         * @param profession {@link #profession}
         * @param age {@link #age}
         * @param sex {@link #sex}
         * @throws NullPointerException {@link #id} or {@link #name} is {@code null}.
         */
        @JsonCreator
        public Player(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("profession") @Nullable String profession, @JsonProperty("age") @Nullable Integer age, @JsonProperty("sex") @Nullable String sex) throws NullPointerException{
            if (id == null) {
                throw new NullPointerException("Player ID cannot be null.");
            } else if (name == null) {
                throw new NullPointerException("Player name cannot be null.");
            }

            this.id = id;
            this.name = name;
            this.profession = profession != null ? profession : "wanderer";
            this.age = age != null ? age : 18;
            this.sex = sex != null ? sex : "unknown";
        }

        /**
         * Retrieves the player's unique ID.
         *
         * @return {@link #id}
         */
        public String getId() {
            return id;
        }

        /**
         * Retrieves the player's name.
         *
         * @return {@link #name}
         */
        public String getName() {
            return name;
        }

        /**
         * Retrieves the player's profession or role.
         *
         * @return {@link #profession}
         */
        public String getProfession() {
            return profession;
        }

        /**
         * Retrieves the player's age.
         *
         * @return {@link #age}
         */
        public int getAge() {
            return age;
        }

        /**
         * Retrieves the player's sex.
         *
         * @return {@link #sex}
         */
        public String getSex() {
            return this.sex;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "id='" + this.id + "'" +
                    "name='" + this.name + "'" +
                    "profession='" + this.profession + "'" +
                    "age=" + this.age +
                    "sex='" + this.sex + "'" +
                    "}";
        }
    }

    @Override
    public String toString() {
        return "PlayerConfig{" +
                "players=" + players +
                '}';
    }
}
