package com.ethanrobins.ai_npc_concept.meta;

/**
 * <b>Sex Enum</b> - Represents the biological or identified sex of an entity with a descriptive label.
 * <p>This enum provides three categories: {@link #MALE}, {@link #FEMALE}, and {@link #UNKNOWN}, each associated
 * with a human-readable 'description' which is used in config files.</p>
 *
 * <br><b>Enum Constants:</b>
 * <ul>
 *   <li>{@link Sex#MALE}</li>
 *   <li>{@link Sex#FEMALE}</li>
 *   <li>{@link Sex#UNKNOWN}</li>
 * </ul>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link Sex#description}</li>
 * </ul>
 *
 * <br><b>Methods:</b>
 * <ul>
 *   <li>{@link #getDescription()}</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>Use the {@code Sex} enum to assign or query the sex of an entity. Each constant stores a string
 * label accessible via {@link #getDescription()}.</p>
 */
public enum Sex {
    /**
     * Identifies the entity as male.
     */
    MALE("male"),
    /**
     * Identifies the entity as female.
     */
    FEMALE("female"),
    /**
     * Identifies the entity as non-binary, undisclosed, or queer.
     */
    UNKNOWN("unknown");

    /**
     * A human-readable string representing the sex identity.
     */
    private final String description;

    /**
     * Constructs a {@link Sex} enum with the provided description.
     *
     * @param description {@link Sex#description}
     * @see Sex
     */
    Sex (String description) {
        this.description = description;
    }

    /**
     * Retrieves the string description of this {@link Sex} constant.
     *
     * @return {@link Sex#description}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts a string value into its corresponding {@link Sex} enum constant.
     * <p>This method is case-insensitive and defaults to {@link Sex#UNKNOWN} if the input does not match a description.</p>
     *
     * @param s The input string representing a sex value.
     * @return A {@link Sex} constant.
     */
    public static Sex fromString(String s) {
        String lowerS = s.toLowerCase();
        switch (lowerS) {
            case "male":
                return MALE;
            case "female" :
                return FEMALE;
            default:
                return UNKNOWN;
        }
    }
}
