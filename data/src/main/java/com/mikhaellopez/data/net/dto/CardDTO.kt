package com.mikhaellopez.data.net.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardDTO(
    @SerialName("name") val name: String,
    @SerialName("frenchName") val frenchName: String? = null,
    @SerialName("japaneseName") val japaneseName: String? = null,
    @SerialName("type") val type: String,
    @SerialName("rarity") val rarity: String,
    @SerialName("pokemonNumber") val pokemonNumber: Int? = null,
    @SerialName("picture") val picture: String,
    @SerialName("illustrator") val illustrator: String,
    @SerialName("wikiLink") val wikiLink: String,
    @SerialName("number") val number: Int
)
