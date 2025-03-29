package com.ethanrobins.ai_npc_concept;

import com.ethanrobins.ai_npc_concept.meta.Sex;
import org.jetbrains.annotations.NotNull;

public class Player implements SessionData{
    private final String id;
    private final String name;
    private final String profession;
    private final int age;
    private final Sex sex;

    public Player(@NotNull String id, @NotNull String name, @NotNull String profession, int age, @NotNull Sex sex) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getProfession() {
        return this.profession;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public Sex getSex() {
        return this.sex;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id: '" + this.id + "'" +
                ", name: '" + this.name + "'" +
                ", profession: '" + this.profession + "'" +
                ", age: " + this.age +
                ", sex: " + this.sex.name() +
                "}";
    }
}
