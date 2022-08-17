package com.mikhaellopez.domain.model

enum class TypeEnum {
    GRASS,
    FIRE,
    WATER,
    LIGHTNING,
    PSYCHIC,
    FIGHTING,
    COLORLESS,
    TRAINER,
    ENERGY;

    companion object {
        fun toTypeEnum(value: String): TypeEnum =
            when (value) {
                "GRASS" -> GRASS
                "FIRE" -> FIRE
                "WATER" -> WATER
                "LIGHTNING" -> LIGHTNING
                "PSYCHIC" -> PSYCHIC
                "FIGHTING" -> FIGHTING
                "COLORLESS" -> COLORLESS
                "TRAINER" -> TRAINER
                "ENERGY" -> ENERGY
                else -> throw RuntimeException("Unknown this type: $value")
            }
    }
}
