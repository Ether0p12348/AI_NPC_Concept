package com.ethanrobins.ai_npc_concept.meta;

public class MetaScore {
    /**
     * This mood's definitions
     */
    private final MetaDef state;
    /**
     * This mood's score
     */
    private int score;

    public MetaScore(MetaDef state, int score) {
        this.state = state;
        this.score = score;
    }

    public MetaScore(MetaDef state) {
        this.state = state;
        this.score = 5; // NEUTRAL
    }

    public MetaScore setScore(int score) {
        this.score = score;
        return this;
    }

    public MetaDef getState() {
        return this.state;
    }

    public int getScore() {
        return this.score;
    }
}
