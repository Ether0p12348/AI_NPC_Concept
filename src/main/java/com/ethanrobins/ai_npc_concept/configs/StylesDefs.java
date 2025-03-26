package com.ethanrobins.ai_npc_concept.configs;

public class StylesDefs extends Defs<StylesDefs.Style> {
    public static class Style extends Defs.Def {
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
