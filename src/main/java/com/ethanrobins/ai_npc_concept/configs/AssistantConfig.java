package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssistantConfig {
    @JsonProperty("default_instructions")
    private String defaultInstructions;
    private List<Assistant> assistants;

    public String getDefaultInstructions() {
        return defaultInstructions;
    }

    public void setDefaultInstructions(String defaultInstructions) {
        this.defaultInstructions = defaultInstructions;
    }

    public List<Assistant> getAssistants() {
        return assistants;
    }

    public void setAssistants(List<Assistant> assistants) {
        this.assistants = assistants;
    }

    public static class Assistant {
        private String id;
        private String name;
        private String profession;
        private int age;
        private String sex;
        private String description;
        private double temperature;
        private Personality personality;
        private List<String> style;
        private Defaults defaults;
        @JsonProperty("style_overrides")
        private Map<String, List<String>> styleOverrides = new HashMap<>();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return this.sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public Personality getPersonality() {
            return personality;
        }

        public void setPersonality(Personality personality) {
            this.personality = personality;
        }

        public List<String> getStyle() {
            return style;
        }

        public void setStyle(List<String> style) {
            this.style = style;
        }

        public Defaults getDefaults() {
            return defaults;
        }

        public void setDefaults(Defaults defaults) {
            this.defaults = defaults;
        }

        public Map<String, List<String>> getStyleOverrides() {
            return styleOverrides;
        }

        public void setStyleOverrides(Map<String, List<String>> styleOverrides) {
            this.styleOverrides = styleOverrides;
        }

        public static class Personality {
            private int empathy;
            private int discipline;
            private int temper;
            private int confidence;
            private int curiosity;
            private int humor;

            public int getEmpathy() {
                return empathy;
            }

            public void setEmpathy(int empathy) {
                this.empathy = empathy;
            }

            public int getDiscipline() {
                return discipline;
            }

            public void setDiscipline(int discipline) {
                this.discipline = discipline;
            }

            public int getTemper() {
                return temper;
            }

            public void setTemper(int temper) {
                this.temper = temper;
            }

            public int getConfidence() {
                return confidence;
            }

            public void setConfidence(int confidence) {
                this.confidence = confidence;
            }

            public int getCuriosity() {
                return curiosity;
            }

            public void setCuriosity(int curiosity) {
                this.curiosity = curiosity;
            }

            public int getHumor() {
                return humor;
            }

            public void setHumor(int humor) {
                this.humor = humor;
            }

            @Override
            public String toString() {
                return "Personality{" +
                        "empathy=" + empathy +
                        ", discipline=" + discipline +
                        ", temper=" + temper +
                        ", confidence=" + confidence +
                        ", curiosity=" + curiosity +
                        ", humor=" + humor +
                        '}';
            }
        }

        public static class Defaults {
            @JsonProperty("emotional_state")
            private String emotionalState;
            private Opinion opinion;
            private Mood mood;

            public String getEmotionalState() {
                return emotionalState;
            }

            public void setEmotionalState(String emotionalState) {
                this.emotionalState = emotionalState;
            }

            public Opinion getOpinion() {
                return opinion;
            }

            public void setOpinion(Opinion opinion) {
                this.opinion = opinion;
            }

            public Mood getMood() {
                return mood;
            }

            public void setMood(Mood mood) {
                this.mood = mood;
            }

            public static class Opinion {
                private int trustworthiness;
                private int respect;
                private int likability;
                private int reliability;
                @JsonProperty("emotional_bond")
                private int emotionalBond;

                public int getTrustworthiness() {
                    return trustworthiness;
                }

                public void setTrustworthiness(int trustworthiness) {
                    this.trustworthiness = trustworthiness;
                }

                public int getRespect() {
                    return respect;
                }

                public void setRespect(int respect) {
                    this.respect = respect;
                }

                public int getLikability() {
                    return likability;
                }

                public void setLikability(int likability) {
                    this.likability = likability;
                }

                public int getReliability() {
                    return reliability;
                }

                public void setReliability(int reliability) {
                    this.reliability = reliability;
                }

                public int getEmotionalBond() {
                    return emotionalBond;
                }

                public void setEmotionalBond(int emotionalBond) {
                    this.emotionalBond = emotionalBond;
                }

                @Override
                public String toString() {
                    return "Opinion{" +
                            "trustworthiness=" + trustworthiness +
                            ", respect=" + respect +
                            ", likeability=" + likability +
                            ", reliability=" + reliability +
                            ", emotionalBond=" + emotionalBond +
                            '}';
                }
            }

            public static class Mood {
                @JsonProperty("social_energy")
                private int socialEnergy;
                @JsonProperty("trust_in_others")
                private int trustInOthers;
                @JsonProperty("emotional_stability")
                private int emotionalStability;
                private int motivation;
                private int pride;
                @JsonProperty("attachment_to_others")
                private int attachmentToOthers;

                public int getSocialEnergy() {
                    return socialEnergy;
                }

                public void setSocialEnergy(int socialEnergy) {
                    this.socialEnergy = socialEnergy;
                }

                public int getTrustInOthers() {
                    return trustInOthers;
                }

                public void setTrustInOthers(int trustInOthers) {
                    this.trustInOthers = trustInOthers;
                }

                public int getEmotionalStability() {
                    return emotionalStability;
                }

                public void setEmotionalStability(int emotionalStability) {
                    this.emotionalStability = emotionalStability;
                }

                public int getMotivation() {
                    return motivation;
                }

                public void setMotivation(int motivation) {
                    this.motivation = motivation;
                }

                public int getPride() {
                    return pride;
                }

                public void setPride(int pride) {
                    this.pride = pride;
                }

                public int getAttachmentToOthers() {
                    return attachmentToOthers;
                }

                public void setAttachmentToOthers(int attachmentToOthers) {
                    this.attachmentToOthers = attachmentToOthers;
                }

                @Override
                public String toString() {
                    return "Mood{" +
                            "socialEnergy=" + socialEnergy +
                            ", trustInOthers=" + trustInOthers +
                            ", emotionalStability=" + emotionalStability +
                            ", motivation=" + motivation +
                            ", pride=" + pride +
                            ", attachmentToOthers=" + attachmentToOthers +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "Defaults{" +
                        "emotionalState='" + emotionalState + '\'' +
                        ", opinion=" + opinion +
                        ", mood=" + mood +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Assistant{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", profession='" + profession + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    ", description'" + description + '\'' +
                    ", temperature=" + temperature +
                    ", personality=" + personality +
                    ", style=" + style +
                    ", defaults=" + defaults +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AssistantsConfig{" +
                "defaultInstructions='" + defaultInstructions + '\'' +
                ", assistants=" + assistants +
                '}';
    }
}
