package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.Assistant;
import com.ethanrobins.ai_npc_concept.Main;
import com.ethanrobins.ai_npc_concept.Player;
import com.ethanrobins.ai_npc_concept.SessionData;

/**
 * <b>SessionTagSet</b> - A {@link TagSet} implementation used to select a {@link SessionData} target (e.g. Assistant or Player).
 * <p>This tag set allows commands to specify either an NPC or a Player by their unique ID using the {@code --npc} or
 * {@code --player} tags respectively.</p>
 *
 * <br><b>Tags Registered:</b>
 * <ul>
 *   <li>{@link SessionTagSet.NpcTag}</li>
 *   <li>{@link SessionTagSet.PlayerTag}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Apply this tag set to allow session-based commands to resolve their target entity using command-line input.
 * Supports {@link Inclusivity#INCLUSIVE} by default.</p>
 *
 * @see NpcTag
 * @see PlayerTag
 */
public class SessionTagSet extends TagSet<SessionData> {

    /**
     * Constructs the {@link SessionTagSet} with predefined {@code --npc} and {@code --player} tags.
     */
    public SessionTagSet() {
        super("session", "Tags that return a SessionData object based on tag arguments.", "", Inclusivity.INCLUSIVE, new NpcTag(), new PlayerTag());
    }

    /**
     * <b>SessionTagSet.NpcTag</b> - A {@link Tag} that returns an {@link Assistant} based on its ID.
     * <p>This tag allows commands to specify which NPC (assistant) to interact with by passing the assistant's ID.</p>
     *
     * <br><b>Methods:</b>
     * <ul>
     *     <li>{@link #use(String)}</li>
     * </ul>
     *
     * <br><b>Example usage:</b>
     * <pre>{@code /session switch --npc "blacksmith_01"}</pre>
     *
     * @see SessionTagSet
     */
    public static class NpcTag extends Tag<Assistant> {

        /**
         * Constructs the {@code --npc} tag with name, description, and usage pattern.
         * @see NpcTag
         */
        public NpcTag() {
            super("--npc", "Sets the npc for this command.", "{command} --npc '{assistant_id}'");
        }

        /**
         * Resolves the {@link Assistant} object matching the provided ID.
         *
         * @param assistantId The assistant ID to match.
         * @return The corresponding {@link Assistant} instance.
         * @throws NullPointerException If no assistant with the given ID exists.
         */
        @Override
        public Assistant use(String assistantId) throws NullPointerException {
            for (Assistant a : Main.getAssistants()) {
                if (a.getId().equals(assistantId)) {
                    return a;
                }
            }
            throw new NullPointerException("No assistant found with id: " + assistantId);
        }
    }

    /**
     * <b>SessionTagSet.PlayerTag</b> - A {@link Tag} that returns a {@link Player} based on their ID.
     * <p>This tag allows commands to identify the target player using their unique player ID.</p>
     *
     * <br><b>Methods:</b>
     * <ul>
     *     <li>{@link #use(String)}</li>
     * </ul>
     *
     * <br><b>Example usage:</b>
     * <pre>{@code > session switch --player "hero_42"}</pre>
     *
     * @see SessionTagSet
     */
    public static class PlayerTag extends Tag<Player> {

        /**
         * Constructs the {@code --player} tag with name, description, and usage pattern.
         * @see PlayerTag
         */
        public PlayerTag() {
            super("--player", "Sets the player for this command.", "{command} --player '{player_id}'");
        }

        /**
         * Resolves the {@link Player} object matching the provided ID.
         *
         * @param playerId The player ID to match.
         * @return The corresponding {@link Player} instance.
         * @throws NullPointerException If no player with the given ID exists.
         */
        @Override
        public Player use(String playerId) throws NullPointerException {
            for (Player p : Main.getPlayers()) {
                if (p.getId().equals(playerId)) {
                    return p;
                }
            }
            throw new NullPointerException("No player found with id: " + playerId);
        }
    }
}
