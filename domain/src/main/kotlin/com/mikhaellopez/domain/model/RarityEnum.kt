package com.mikhaellopez.domain.model

enum class RarityEnum {
    NONE,
    COMMON,
    UNCOMMON,
    RARE,
    RARE_HOLO;

    companion object {
        fun toRarityEnum(value: String): RarityEnum =
            when (value) {
                "NONE" -> NONE
                "COMMON" -> COMMON
                "UNCOMMON" -> UNCOMMON
                "RARE" -> RARE
                "RARE_HOLO" -> RARE_HOLO
                else -> throw RuntimeException("Unknown this rarity type: $value")
            }
    }
}