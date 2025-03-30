package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>StylesDefs</b> - A configuration container for communication and interaction styles.
 * <p>Extends {@link Defs} to provide a list of {@link StylesDefs.Style} definitions, which represent expressive
 * characteristics such as tone, vocabulary, or body language. These styles influence how assistants behave or
 * communicate during interaction.</p>
 *
 * <b>Usage:</b>
 * <p>Used to define stylistic behavior in NPC dialogue, including roleplaying tone, formality, and other traits.</p>
 *
 * @see StylesDefs.Style
 */
public class StylesDefs extends Defs<StylesDefs.Style> {
    /**
     * Constructs a {@link StylesDefs} object from JSON.
     *
     * @param styleMap {@link #defs}
     */
    @JsonCreator
    public StylesDefs(Map<String, Style> styleMap) {
        super(() -> {
            List<Style> l = new ArrayList<>();
            styleMap.forEach((k, v) -> l.add(new Style(k, v.id, v.name, v.description)));

            return l.toArray(new Style[0]);
        });
    }

    /**
     * <b>StylesDefs.Style</b> - Represents a single communication or behavioral style.
     * <p>Extends {@link Defs.Def} and includes an identifier, name, description, and internal ID.
     * Unlike mood or personality traits, styles are often used to guide outward expression rather than internal state.</p>
     *
     * <b>Usage:</b>
     * <p>Used to define how an assistant behaves when interacting with players or other NPCs—e.g., polite, aggressive, humorous.</p>
     *
     * @see StylesDefs
     */
    public static class Style extends Defs.Def {
        /**
         * Constructs a {@link Style} object from JSON.
         *
         * @param identifier {@link #identifier}
         * @param id {@link #id}
         * @param name {@link #name}
         * @param description {@link #description}
         * @throws NullPointerException if {@link #id} or {@link #name} is {@code null}.
         */
        @JsonCreator
        public Style(@JsonProperty("identifier") @Nullable String identifier, @JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("description") @Nullable String description) throws NullPointerException {
            super(identifier, id, name, description);
        }

        @Override
        public String toString() {
            return "Style{" +
                    "identifier='" + identifier + '\'' +
                    ", id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StyleDefs{" +
                "styles=" + defs +
                '}';
    }
}
