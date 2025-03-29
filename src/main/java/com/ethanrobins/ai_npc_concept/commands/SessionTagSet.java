package com.ethanrobins.ai_npc_concept.commands;

import com.ethanrobins.ai_npc_concept.Assistant;
import com.ethanrobins.ai_npc_concept.Main;
import com.ethanrobins.ai_npc_concept.Player;
import com.ethanrobins.ai_npc_concept.SessionData;

public class SessionTagSet extends TagSet<SessionData> {

    public SessionTagSet() {
        super("session", "Tags that return a SessionData object based on tag arguments.", "", Inclusivity.INCLUSIVE, new NpcTag(), new PlayerTag());
    }

    public static class NpcTag extends Tag<Assistant> {

        public NpcTag() {
            super("--npc", "Sets the npc for this command.", "{command} --npc '{assistant_id}'");
        }

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

    public static class PlayerTag extends Tag<Player> {

        public PlayerTag() {
            super("--player", "Sets the player for this command.", "{command} --player '{player_id}'");
        }

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
