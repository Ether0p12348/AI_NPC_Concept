package com.ethanrobins.ai_npc_concept.meta;

public class MetaScore {
    /**
     * This mood's definitions
     */
    private final MetaDef moodState;
    /**
     * This mood's score
     */
    private int score;

    public MetaScore(MetaDef moodState, int score) {
        this.moodState = moodState;
        this.score = score;
    }

    public MetaScore(MetaDef moodState) {
        this.moodState = moodState;
        this.score = 5; // NEUTRAL
    }

    public MetaScore setScore(int score) {
        this.score = score;
        return this;
    }

    public MetaDef getMoodState() {
        return this.moodState;
    }

    public int getScore() {
        return this.score;
    }
}
