package com.ethanrobins.ai_npc_concept.configs;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.ArrayList;
import java.util.List;

public abstract class Defs<T extends Defs.Def> {
    protected final List<T> defs = new ArrayList<>();

    public List<T> getDefs() {
        return defs;
    }

    @JsonAnySetter
    public void addDef(String identifier, T def) {
        def.setIdentifier(identifier);
        this.defs.add(def);
    }

    public static abstract class Def {
        protected String identifier;
        protected String id;
        protected String name;
        protected String description;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

//        @Override
//        public String toString() {
//            return  + "Def{" +
//                    "identifier='" + identifier + '\'' +
//                    ", id='" + id + '\'' +
//                    ", name='" + name + '\'' +
//                    ", description='" + description + '\'' +
//                    '}';
//        }

        @Override
        public abstract String toString();
    }

//    @Override
//    public String toString() {
//        return "Defs{" +
//                "styles=" + defs +
//                '}';
//    }

    @Override
    public abstract String toString();
}
