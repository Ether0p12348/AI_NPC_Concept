package com.ethanrobins.ai_npc_concept.meta;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <b>MetaDef</b> - Represents a scored metadata definition used to describe character traits or emotional states.
 * <p>This record holds essential metadata attributes such as an identifier, name, description, and optional
 * descriptive strings for low, neutral, and high score interpretations.</p>
 *
 * <br><b>Fields:</b>
 * <ul>
 *   <li>{@link #id} - A unique internal identifier used for lookups and references.</li>
 *   <li>{@link #identifier} - An optional external-facing identifier or alias.</li>
 *   <li>{@link #name} - The display name for this metadata (e.g., "Happiness").</li>
 *   <li>{@link #description} - A general-purpose description explaining this trait or definition.</li>
 *   <li>{@link #lowDef} - A short description of what low scores (typically 0–3) indicate for this trait.</li>
 *   <li>{@link #neutralDef} - A description for medium or neutral scores (typically 4–6).</li>
 *   <li>{@link #highDef} - A description for high scores (typically 7–9).</li>
 * </ul>
 *
 * <br><b>Usage:</b>
 * <p>{@code MetaDef} objects are used in systems where traits, moods, or behaviors are quantified via scores.
 * This allows dynamic NPC or character descriptions to reflect their internal or external state based on score ranges.</p>
 *
 * <b>Example:</b>
 * <pre>{@code
 * MetaDef happiness = new MetaDef(
 *     "001",
 *     "happiness",
 *     "Happiness",
 *     "Describes the assistant's general sense of joy and satisfaction.",
 *     "Sad or withdrawn",
 *     "Content or emotionally stable",
 *     "Joyful or euphoric"
 * );
 * }</pre>
 *
 * @param id A required unique identifier used for internal referencing.
 * @param identifier An optional alias or external ID used in configuration or export.
 * @param name A human-readable label used in interfaces or descriptions.
 * @param description A general overview of what this metadata definition represents.
 * @param lowDef A short description for low score ranges (0–3); may be {@code null}.
 * @param neutralDef A description for average score ranges (4–6); may be {@code null}.
 * @param highDef A description for high score ranges (7–9); may be {@code null}.
 */
public record MetaDef(@NotNull String id, @Nullable String identifier, @NotNull String name, @NotNull String description, @Nullable String lowDef, @Nullable String neutralDef, @Nullable String highDef) {}
