package com.mikhaellopez.domain.model

data class Card(
    val name: String,
    val frenchName: String?,
    val japaneseName: String?,
    val type: TypeEnum,
    val rarity: RarityEnum,
    val pokemonNumber: Int?,
    val picture: String,
    val illustrator: String,
    val wikiLink: String,
    val number: Int,
    val isCheck: Boolean
)