package com.ethanrobins.ai_npc_concept.configs;

public class OpinionDefs extends Defs<OpinionDefs.Opinion> {
    public static class Opinion extends Defs.Def {
        private Scores scores;

        public Scores getScores() {
            return scores;
        }

        public void setScores(Scores scores) {
            this.scores = scores;
        }

        public static class Scores {
            private String low;
            private String neutral;
            private String high;

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getNeutral() {
                return neutral;
            }

            public void setNeutral(String neutral) {
                this.neutral = neutral;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            @Override
            public String toString() {
                return "Scores{" +
                        "low='" + low + '\'' +
                        ", medium='" + neutral + '\'' +
                        ", high='" + high + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Opinion{" +
                    "identifier='" + identifier + '\'' +
                    ", id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", scores=" + scores +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OpinionDefs{" +
                "opinions=" + defs +
                '}';
    }
}
