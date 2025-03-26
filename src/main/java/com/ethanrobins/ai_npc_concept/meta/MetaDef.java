package com.ethanrobins.ai_npc_concept.meta;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A score can be from 0-9 (base-10)
 * @param id          id
 * @param name        display-name
 * @param description description
 * @param lowDef      Score 0-3 description
 * @param neutralDef  Score 4-6 description
 * @param highDef     Score 7-9 description
 */
public record MetaDef(@NotNull String id, @NotNull String name, @NotNull String description, @Nullable String lowDef, @Nullable String neutralDef, @Nullable String highDef) {}
