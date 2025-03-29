package com.ethanrobins.ai_npc_concept;

import com.ethanrobins.ai_npc_concept.meta.Sex;

public interface SessionData {
    String getId();
    String getName();
    String getProfession();
    int getAge();
    Sex getSex();
}
