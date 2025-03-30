package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * <b>AssistantConfig</b> - Represents a top-level configuration container for all AI assistant definitions.
 * <p>This configuration class is deserialized from {@code assistants.json} and is used to preload all available
 * assistants along with their default instruction block and individual characteristics such as personality,
 * temperature, style preferences, and emotional state.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link AssistantConfig#defaultInstructions}</li>
 *   <li>{@link AssistantConfig#assistants}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link AssistantConfig#getDefaultInstructions()}</li>
 *   <li>{@link AssistantConfig#getAssistants()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>This class is typically used during application startup to load and register available assistants into memory.
 * Assistants are then accessed by their IDs and used to instantiate runtime representations in {@link com.ethanrobins.ai_npc_concept.Assistant}.</p>
 *
 * @see AssistantConfig.Assistant
 */
public class AssistantConfig {
    /**
     * Default system-level instruction string for assistants.
     */
    private final String defaultInstructions;
    /**
     * A list of assistant definitions loaded from configuration.
     */
    private final Assistant[] assistants;

    /**
     * Constructs an {@link AssistantConfig} object from JSON.
     *
     * @param defaultInstructions {@link #defaultInstructions}
     * @param assistants {@link #assistants}
     */
    @JsonCreator
    public AssistantConfig(@JsonProperty("default_instructions") @Nullable String defaultInstructions, @JsonProperty("assistants") @Nullable Assistant[] assistants) {
        this.defaultInstructions = defaultInstructions != null ? defaultInstructions : "";
        this.assistants = assistants != null ? assistants : new Assistant[0];
    }

    /**
     * Retrieves the default instruction string for all assistants.
     *
     * @return {@link AssistantConfig#defaultInstructions}
     */
    public String getDefaultInstructions() {
        return defaultInstructions;
    }

    /**
     * Retrieves the list of all assistant definitions loaded from configuration.
     *
     * @return {@link AssistantConfig#assistants}
     */
    public List<Assistant> getAssistants() {
        return Arrays.stream(assistants).toList();
    }

    /**
     * <b>AssistantConfig.Assistant</b> - Defines an individual assistant entity and its configuration profile.
     * <p>This class encapsulates a wide range of assistant properties, including identity, description, AI temperature,
     * core personality metrics, interaction styles, default emotional state, and conditional style overrides.</p>
     *
     * <br><b>Fields:</b>
     * <ul>
     *   <li>{@link #id}</li>
     *   <li>{@link #name}</li>
     *   <li>{@link #profession}</li>
     *   <li>{@link #age}</li>
     *   <li>{@link #sex}</li>
     *   <li>{@link #description}</li>
     *   <li>{@link #temperature}</li>
     *   <li>{@link #style}</li>
     *   <li>{@link #personality}</li>
     *   <li>{@link #defaults}</li>
     *   <li>{@link #styleOverrides}</li>
     * </ul>
     *
     * <br><b>Methods:</b>
     * <ul>
     *   <li>{@link #getId()}</li>
     *   <li>{@link #getName()}</li>
     *   <li>{@link #getProfession()}</li>
     *   <li>{@link #getAge()}</li>
     *   <li>{@link #getSex()}</li>
     *   <li>{@link #getDescription()}</li>
     *   <li>{@link #getTemperature()}</li>
     *   <li>{@link #getStyle()}</li>
     *   <li>{@link #getPersonality()}</li>
     *   <li>{@link #getDefaults()}</li>
     *   <li>{@link #getStyleOverrides()}</li>
     * </ul>
     *
     * <b>Usage:</b>
     * <p>Used to configure each AI assistant available in the system. This configuration is later mapped to a
     * runtime {@link com.ethanrobins.ai_npc_concept.Assistant} object that holds live emotional state and dynamic opinions.</p>
     *
     * @see AssistantConfig
     * @see AssistantConfig.Assistant.Personality
     * @see AssistantConfig.Assistant.Defaults
     */
    public static class Assistant {
        /**
         * The unique identifier for the assistant.
         */
        private final String id;
        /**
         * The name or display title of the assistant.
         */
        private final String name;
        /**
         * The assistant’s profession or role in the world (e.g., "Blacksmith", "Healer").
         */
        private final String profession;
        /**
         * The assistant’s age in years.
         */
        private final int age;
        /**
         * The assistant’s biological or identified sex.
         */
        private final String sex;
        /**
         * A brief descriptive summary of the assistant's personality, background, or purpose.
         */
        private final String description;
        /**
         * The assistant's temperature value for controlling randomness in AI responses.
         */
        private final double temperature;
        /**
         * A collection of personality trait values that define the assistant’s behavioral core.
         */
        private final Personality personality;
        /**
         * A list of style identifiers representing the assistant’s primary communication tone or expression.
         */
        private final String[] style;
        /**
         * Defines the assistant’s default mood, emotional state, and player opinions.
         */
        private final Defaults defaults;
        /**
         * A map of conditional style overrides applied when specific {@link com.ethanrobins.ai_npc_concept.Assistant.OverrideCondition}s are met.
         * <p>The key represents the override condition, and the value is a list of style identifiers to apply.</p>
         */
        private final Map<String, String[]> styleOverrides;

        /**
         * Constructs a {@link Defaults} object from JSON.
         *
         * @param id {@link #id}
         * @param name {@link #name}
         * @param profession {@link #profession}
         * @param age {@link #age}
         * @param sex {@link #sex}
         * @param description {@link #description}
         * @param temperature {@link #temperature}
         * @param personality {@link #personality}
         * @param style {@link #style}
         * @param defaults {@link #defaults}
         * @param styleOverrides {@link #styleOverrides}
         * @throws NullPointerException if {@link #id} or {@link #name} is {@code null}.
         */
        @JsonCreator
        public Assistant(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("profession") @Nullable String profession, @JsonProperty("age") @Nullable Integer age, @JsonProperty("sex") @Nullable String sex, @JsonProperty("description") @Nullable String description, @JsonProperty("temperature") @Nullable Double temperature, @JsonProperty("personality") @Nullable Personality personality, @JsonProperty("style") @Nullable String[] style, @JsonProperty("defaults") @Nullable Defaults defaults, @JsonProperty("style_overrides") @Nullable Map<String, String[]> styleOverrides) {
            if (id == null) {
                throw new NullPointerException("AssistantConfig.Assistant.id cannot be null.");
            } else if (name == null) {
                throw new NullPointerException("AssistantConfig.Assistant.name cannot be null.");
            }

            this.id = id;
            this.name = name;
            this.profession = profession != null ? profession : "wanderer";
            this.age = age != null ? age : 18;
            this.sex = sex != null ? sex : "unknown";
            this.description = description != null ? description : "";
            this.temperature = temperature != null ? temperature : 0.7;
            this.personality = personality != null ? personality : new Personality(null, null, null, null, null, null);
            this.style = style != null ? style : new String[0];
            this.defaults = defaults != null ? defaults : new Defaults(null, null, null);
            this.styleOverrides = styleOverrides != null ? styleOverrides : new HashMap<>();
        }

        /**
         * Retrieves the assistant's unique identifier.
         *
         * @return {@link AssistantConfig.Assistant#id}
         */
        public String getId() {
            return id;
        }

        /**
         * Retrieves the assistant's name.
         *
         * @return {@link AssistantConfig.Assistant#name}
         */
        public String getName() {
            return name;
        }

        /**
         * Retrieves the assistant's profession or role.
         *
         * @return {@link AssistantConfig.Assistant#profession}
         */
        public String getProfession() {
            return profession;
        }

        /**
         * Retrieves the assistant's age.
         *
         * @return {@link AssistantConfig.Assistant#age}
         */
        public int getAge() {
            return age;
        }

        /**
         * Retrieves the assistant's biological or identified sex.
         *
         * @return {@link AssistantConfig.Assistant#sex}
         */
        public String getSex() {
            return this.sex;
        }

        /**
         * Retrieves the assistant's description or background information.
         *
         * @return {@link AssistantConfig.Assistant#description}
         */
        public String getDescription() {
            return description;
        }

        /**
         * Retrieves the assistant's configured temperature value.
         *
         * @return {@link AssistantConfig.Assistant#temperature}
         */
        public double getTemperature() {
            return temperature;
        }

        /**
         * Retrieves the assistant's personality trait configuration.
         *
         * @return {@link AssistantConfig.Assistant#personality}
         */
        public Personality getPersonality() {
            return personality;
        }

        /**
         * Retrieves the assistant's list of default style identifiers.
         *
         * @return {@link AssistantConfig.Assistant#style}
         */
        public List<String> getStyle() {
            return Arrays.stream(style).toList();
        }

        /**
         * Retrieves the assistant's default values for mood, opinions, and emotional state.
         *
         * @return {@link AssistantConfig.Assistant#defaults}
         */
        public Defaults getDefaults() {
            return defaults;
        }

        /**
         * Retrieves the assistant’s conditional style overrides mapped by condition string.
         *
         * @return {@link AssistantConfig.Assistant#styleOverrides}
         */
        public Map<String, List<String>> getStyleOverrides() {
            Map<String, List<String>> result = new HashMap<>();

            for (Map.Entry<String, String[]> entry : styleOverrides.entrySet()) {
                result.put(entry.getKey(), Arrays.stream(entry.getValue()).toList());
            }

            return result;
        }

        /**
         * <b>AssistantConfig.Assistant.Personality</b> - Represents the assistant's personality profile.
         * <p>This class stores fixed numeric values for key personality traits that influence behavior and communication
         * style. These values are typically between 1 and 10 and are used during assistant initialization.</p>
         *
         * <br><b>Fields:</b>
         * <ul>
         *   <li>{@link #empathy}</li>
         *   <li>{@link #discipline}</li>
         *   <li>{@link #temper}</li>
         *   <li>{@link #confidence}</li>
         *   <li>{@link #curiosity}</li>
         *   <li>{@link #humor}</li>
         * </ul>
         *
         * <br><b>Methods:</b>
         * <ul>
         *   <li>{@link #getEmpathy()}</li>
         *   <li>{@link #getDiscipline()}</li>
         *   <li>{@link #getTemper()}</li>
         *   <li>{@link #getConfidence()}</li>
         *   <li>{@link #getCuriosity()}</li>
         *   <li>{@link #getHumor()}</li>
         * </ul>
         *
         * <b>Usage:</b>
         * <p>This class is loaded from configuration and used to instantiate the {@link com.ethanrobins.ai_npc_concept.Assistant.Personality}
         * record in live assistant objects.</p>
         *
         * @see Assistant
         */
        public static class Personality {
            /**
             * The assistant’s capacity to emotionally relate to others and demonstrate understanding or compassion.
             */
            private final int empathy;
            /**
             * The assistant’s tendency to follow structure, rules, and maintain focus and order.
             */
            private final int discipline;
            /**
             * The assistant’s sensitivity to irritation, impulsiveness, or emotional reactivity.
             */
            private final int temper;
            /**
             * The assistant’s level of self-assurance and assertiveness in interactions and decision-making.
             */
            private final int confidence;
            /**
             * The assistant’s interest in learning, exploring, or seeking out new information and ideas.
             */
            private final int curiosity;
            /**
             * The assistant’s use of humor, playfulness, or lighthearted communication.
             */
            private final int humor;

            /**
             * Constructs a {@link Personality} object from JSON.
             *
             * @param empathy {@link #empathy}
             * @param discipline {@link #discipline}
             * @param temper {@link #temper}
             * @param confidence {@link #confidence}
             * @param curiosity {@link #curiosity}
             * @param humor {@link #humor}
             * @throws IllegalArgumentException if any value is not between {@code 0} and {@code 9}.
             */
            @JsonCreator
            public Personality(@JsonProperty("empathy") @Nullable Integer empathy, @JsonProperty("discipline") @Nullable Integer discipline, @JsonProperty("temper") @Nullable Integer temper, @JsonProperty("confidence") @Nullable Integer confidence, @JsonProperty("curiosity") @Nullable Integer curiosity, @JsonProperty("humor") @Nullable Integer humor) throws IllegalArgumentException {
                this.empathy = empathy != null ? empathy : 5;
                this.discipline = discipline != null ? discipline : 5;
                this.temper = temper != null ? temper : 5;
                this.confidence = confidence != null ? confidence : 5;
                this.curiosity = curiosity != null ? curiosity : 5;
                this.humor = humor != null ? humor : 5;

                if (this.empathy < 0 || this.empathy > 9) {
                    throw new IllegalArgumentException("Personality trait empathy must be between 0 and 9.");
                }
                if (this.discipline < 0 || this.discipline > 9) {
                    throw new IllegalArgumentException("Personality trait discipline must be between 0 and 9.");
                }
                if (this.temper < 0 || this.temper > 9) {
                    throw new IllegalArgumentException("Personality trait temper must be between 0 and 9.");
                }
                if (this.confidence < 0 || this.confidence > 9) {
                    throw new IllegalArgumentException("Personality trait confidence must be between 0 and 9.");
                }
                if (this.curiosity < 0 || this.curiosity > 9) {
                    throw new IllegalArgumentException("Personality trait curiosity must be between 0 and 9.");
                }
                if (this.humor < 0 || this.humor > 9) {
                    throw new IllegalArgumentException("Personality trait humor must be between 0 and 9.");
                }
            }

            /**
             * Retrieves the assistant's empathy score.
             *
             * @return {@link AssistantConfig.Assistant.Personality#empathy}
             */
            public int getEmpathy() {
                return empathy;
            }

            /**
             * Retrieves the assistant's discipline score.
             *
             * @return {@link AssistantConfig.Assistant.Personality#discipline}
             */
            public int getDiscipline() {
                return discipline;
            }

            /**
             * Retrieves the assistant's temper score.
             *
             * @return {@link AssistantConfig.Assistant.Personality#temper}
             */
            public int getTemper() {
                return temper;
            }

            /**
             * Retrieves the assistant's confidence score.
             *
             * @return {@link AssistantConfig.Assistant.Personality#confidence}
             */
            public int getConfidence() {
                return confidence;
            }

            /**
             * Retrieves the assistant's curiosity score.
             *
             * @return {@link AssistantConfig.Assistant.Personality#curiosity}
             */
            public int getCuriosity() {
                return curiosity;
            }

            /**
             * Retrieves the assistant's humor score.
             *
             * @return {@link AssistantConfig.Assistant.Personality#humor}
             */
            public int getHumor() {
                return humor;
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

        /**
         * <b>AssistantConfig.Assistant.Defaults</b> - Represents default emotional and mood configuration for an assistant.
         * <p>This class defines the assistant’s initial internal state when first loaded, including emotional labels,
         * opinion templates, and baseline mood metrics.</p>
         *
         * <br><b>Fields:</b>
         * <ul>
         *   <li>{@link #emotionalState}</li>
         *   <li>{@link #opinion}</li>
         *   <li>{@link #mood}</li>
         * </ul>
         *
         * <br><b>Methods:</b>
         * <ul>
         *   <li>{@link #getEmotionalState()}</li>
         *   <li>{@link #getOpinion()}</li>
         *   <li>{@link #getMood()}</li>
         * </ul>
         *
         * <b>Usage:</b>
         * <p>This block is converted into the assistant’s live mood and opinion state upon creation, influencing both
         * dialogue and behavioral models.</p>
         *
         * @see Assistant
         * @see AssistantConfig.Assistant.Defaults.Opinion
         * @see AssistantConfig.Assistant.Defaults.Mood
         */
        public static class Defaults {
            /**
             * The assistant’s default emotional state label (e.g., "calm", "frustrated", "cheerful").
             */
            private final String emotionalState;
            /**
             * The assistant’s default opinion values toward a player or group of players.
             */
            private final Opinion opinion;
            /**
             * The assistant’s default mood values representing internal state stability, energy, and trust.
             */
            private final Mood mood;

            /**
             * Constructs a {@link Defaults} object from JSON.
             *
             * @param emotionalState {@link #emotionalState}
             * @param opinion {@link #opinion}
             * @param mood {@link #mood}
             */
            @JsonCreator
            public Defaults(@JsonProperty("emotional_state") @Nullable String emotionalState, @JsonProperty("opinion") @Nullable Opinion opinion, @JsonProperty("mood") @Nullable Mood mood) {
                this.emotionalState = emotionalState != null ? emotionalState : "calm";
                this.opinion = opinion != null ? opinion : new Opinion(null, null, null, null, null);
                this.mood = mood != null ? mood : new Mood(null, null, null, null, null, null);
            }

            /**
             * Retrieves the assistant’s initial emotional state label.
             *
             * @return {@link AssistantConfig.Assistant.Defaults#emotionalState}
             */
            public String getEmotionalState() {
                return emotionalState;
            }
            /**
             * Retrieves the assistant’s default opinion scores toward the player.
             *
             * @return {@link AssistantConfig.Assistant.Defaults#opinion}
             */
            public Opinion getOpinion() {
                return opinion;
            }

            /**
             * Retrieves the assistant’s default mood scores.
             *
             * @return {@link AssistantConfig.Assistant.Defaults#mood}
             */
            public Mood getMood() {
                return mood;
            }

            /**
             * <b>AssistantConfig.Assistant.Defaults.Opinion</b> - Defines default subjective scores the assistant holds toward players.
             * <p>These values reflect the assistant’s emotional and social stance toward others at startup and directly
             * influence relationship dynamics and how dialogue is delivered.</p>
             *
             * <br><b>Fields:</b>
             * <ul>
             *   <li>{@link #trustworthiness}</li>
             *   <li>{@link #respect}</li>
             *   <li>{@link #likability}</li>
             *   <li>{@link #reliability}</li>
             *   <li>{@link #emotionalBond}</li>
             * </ul>
             *
             * <br><b>Methods:</b>
             * <ul>
             *   <li>{@link #getTrustworthiness()}</li>
             *   <li>{@link #getRespect()}</li>
             *   <li>{@link #getLikability()}</li>
             *   <li>{@link #getReliability()}</li>
             *   <li>{@link #getEmotionalBond()}</li>
             * </ul>
             *
             * <b>Usage:</b>
             * <p>This configuration is used to initialize {@link com.ethanrobins.ai_npc_concept.Assistant.Opinion}
             * for newly created assistant-player relationships.</p>
             *
             * @see Defaults
             */
            public static class Opinion {
                /**
                 * The assistant’s baseline trust in the player.
                 */
                private final int trustworthiness;
                /**
                 * The level of respect the assistant initially holds toward the player.
                 */
                private final int respect;
                /**
                 * How likable or friendly the assistant perceives the player to be.
                 */
                private final int likability;
                /**
                 * The assistant’s belief in the player’s dependability and ability to follow through.
                 */
                private final int reliability;
                /**
                 * The emotional closeness or bond the assistant feels toward the player.
                 */
                private final int emotionalBond;

                /**
                 * Constructs an {@link Opinion} object from JSON.
                 *
                 * @param trustworthiness {@link #trustworthiness}
                 * @param respect {@link #respect}
                 * @param likability {@link #likability}
                 * @param reliability {@link #reliability}
                 * @param emotionalBond {@link #emotionalBond}
                 * @throws IllegalArgumentException if any value is not between {@code 0} and {@code 9}.
                 */
                @JsonCreator
                public Opinion(@JsonProperty("trustworthiness") @Nullable Integer trustworthiness, @JsonProperty("respect") @Nullable Integer respect, @JsonProperty("likability") @Nullable Integer likability, @JsonProperty("reliability") @Nullable Integer reliability, @JsonProperty("emotional_bond") @Nullable Integer emotionalBond) throws IllegalArgumentException {
                    this.trustworthiness = trustworthiness != null ? trustworthiness : 5;
                    this.respect = respect != null ? respect : 5;
                    this.likability = likability != null ? likability : 5;
                    this.reliability = reliability != null ? reliability : 5;
                    this.emotionalBond = emotionalBond != null ? emotionalBond : 5;

                    if (this.trustworthiness < 0 || this.trustworthiness > 9) {
                        throw new IllegalArgumentException("Opinion trustworthiness must be between 0 and 9.");
                    }
                    if (this.respect < 0 || this.respect > 9) {
                        throw new IllegalArgumentException("Opinion respect must be between 0 and 9.");
                    }
                    if (this.likability < 0 || this.likability > 9) {
                        throw new IllegalArgumentException("Opinion likability must be between 0 and 9.");
                    }
                    if (this.reliability < 0 || this.reliability > 9) {
                        throw new IllegalArgumentException("Opinion reliability must be between 0 and 9.");
                    }
                    if (this.emotionalBond < 0 || this.emotionalBond > 9) {
                        throw new IllegalArgumentException("Opinion emotionalBond must be between 0 and 9.");
                    }
                }

                /**
                 * Retrieves the default trustworthiness score.
                 *
                 * @return {@link AssistantConfig.Assistant.Defaults.Opinion#trustworthiness}
                 */
                public int getTrustworthiness() {
                    return trustworthiness;
                }

                /**
                 * Retrieves the default respect score.
                 *
                 * @return {@link AssistantConfig.Assistant.Defaults.Opinion#respect}
                 */
                public int getRespect() {
                    return respect;
                }

                /**
                 * Retrieves the default likability score.
                 *
                 * @return {@link AssistantConfig.Assistant.Defaults.Opinion#likability}
                 */
                public int getLikability() {
                    return likability;
                }

                /**
                 * Retrieves the default reliability score.
                 *
                 * @return {@link AssistantConfig.Assistant.Defaults.Opinion#reliability}
                 */
                public int getReliability() {
                    return reliability;
                }

                /**
                 * Retrieves the default emotional bond score.
                 *
                 * @return {@link AssistantConfig.Assistant.Defaults.Opinion#emotionalBond}
                 */
                public int getEmotionalBond() {
                    return emotionalBond;
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

            /**
             * <b>AssistantConfig.Assistant.Defaults.Mood</b> - Defines baseline emotional and motivational values.
             * <p>These values represent the assistant’s psychological state and are used to generate natural behaviors
             * and responses. All values typically range from 1 to 10.</p>
             *
             * <br><b>Fields:</b>
             * <ul>
             *   <li>{@link #socialEnergy}</li>
             *   <li>{@link #trustInOthers}</li>
             *   <li>{@link #emotionalStability}</li>
             *   <li>{@link #motivation}</li>
             *   <li>{@link #pride}</li>
             *   <li>{@link #attachmentToOthers}</li>
             * </ul>
             *
             * <br><b>Methods:</b>
             * <ul>
             *   <li>{@link #getSocialEnergy()}</li>
             *   <li>{@link #getTrustInOthers()}</li>
             *   <li>{@link #getEmotionalStability()}</li>
             *   <li>{@link #getMotivation()}</li>
             *   <li>{@link #getPride()}</li>
             *   <li>{@link #getAttachmentToOthers()}</li>
             * </ul>
             *
             * <b>Usage:</b>
             * <p>Used to generate a {@link com.ethanrobins.ai_npc_concept.Assistant.Mood} record during assistant instantiation.</p>
             *
             * @see Defaults
             */
            public static class Mood {
                /**
                 * The assistant’s baseline social energy — their drive for interaction and presence of others.
                 */
                private final int socialEnergy;
                /**
                 * The default level of trust the assistant places in others generally.
                 */
                private final int trustInOthers;
                /**
                 * The assistant’s emotional stability — their resistance to stress or sudden emotional shifts.
                 */
                private final int emotionalStability;
                /**
                 * The assistant’s internal drive and willingness to act or engage.
                 */
                private final int motivation;
                /**
                 * The assistant’s level of pride, ego, or self-esteem.
                 */
                private final int pride;
                /**
                 * The assistant’s tendency to emotionally attach to others.
                 */
                private final int attachmentToOthers;

                /**
                 * Constructs a {@link Mood} object from JSON.
                 *
                 * @param socialEnergy {@link #socialEnergy}
                 * @param trustInOthers {@link #trustInOthers}
                 * @param emotionalStability {@link #emotionalStability}
                 * @param motivation {@link #motivation}
                 * @param pride {@link #pride}
                 * @param attachmentToOthers {@link #attachmentToOthers}
                 * @throws IllegalArgumentException if any value is not between {@code 0} and {@code 9}.
                 */
                @JsonCreator
                public Mood(@JsonProperty("social_energy") @Nullable Integer socialEnergy, @JsonProperty("trust_in_others") @Nullable Integer trustInOthers, @JsonProperty("emotional_stability") @Nullable Integer emotionalStability, @JsonProperty("motivation") @Nullable Integer motivation, @JsonProperty("pride") @Nullable Integer pride, @JsonProperty("attachment_to_others") @Nullable Integer attachmentToOthers) throws IllegalArgumentException {
                    this.socialEnergy = socialEnergy != null ? socialEnergy : 5;
                    this.trustInOthers = trustInOthers != null ? trustInOthers : 5;
                    this.emotionalStability = emotionalStability != null ? emotionalStability : 5;
                    this.motivation = motivation != null ? motivation : 5;
                    this.pride = pride != null ? pride : 5;
                    this.attachmentToOthers = attachmentToOthers != null ? attachmentToOthers : 5;

                    if (this.socialEnergy < 0 || this.socialEnergy > 9) {
                        throw new IllegalArgumentException("Mood socialEnergy must be between 0 and 9.");
                    }
                    if (this.trustInOthers < 0 || this.trustInOthers > 9) {
                        throw new IllegalArgumentException("Mood trustInOthers must be between 0 and 9.");
                    }
                    if (this.emotionalStability < 0 || this.emotionalStability > 9) {
                        throw new IllegalArgumentException("Mood emotionalStability must be between 0 and 9.");
                    }
                    if (this.motivation < 0 || this.motivation > 9) {
                        throw new IllegalArgumentException("Mood motivation must be between 0 and 9.");
                    }
                    if (this.pride < 0 || this.pride > 9) {
                        throw new IllegalArgumentException("Mood pride must be between 0 and 9.");
                    }
                    if (this.attachmentToOthers < 0 || this.attachmentToOthers > 9) {
                        throw new IllegalArgumentException("Mood attachmentToOthers must be between 0 and 9.");
                    }
                }

                public int getSocialEnergy() {
                    return socialEnergy;
                }

                public int getTrustInOthers() {
                    return trustInOthers;
                }

                public int getEmotionalStability() {
                    return emotionalStability;
                }

                public int getMotivation() {
                    return motivation;
                }

                public int getPride() {
                    return pride;
                }

                public int getAttachmentToOthers() {
                    return attachmentToOthers;
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
