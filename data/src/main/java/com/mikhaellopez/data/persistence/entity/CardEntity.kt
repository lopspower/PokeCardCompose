package com.mikhaellopez.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mikhaellopez.data.persistence.entity.CardEntity.Companion.CARD_TABLE

@Entity(tableName = CARD_TABLE)
class CardEntity(
    @PrimaryKey @ColumnInfo(name = CARD_NAME) val name: String,
    @ColumnInfo(name = CARD_FRENCH_NAME) val frenchName: String?,
    @ColumnInfo(name = CARD_JAPANESE_NAME) val japaneseName: String?,
    @ColumnInfo(name = CARD_TYPE) val type: String,
    @ColumnInfo(name = CARD_RARITY) val rarity: String,
    @ColumnInfo(name = CARD_POKEMON_NUMBER) val pokemonNumber: Int?,
    @ColumnInfo(name = CARD_PICTURE) val picture: String,
    @ColumnInfo(name = CARD_ILLUSTRATOR) val illustrator: String,
    @ColumnInfo(name = CARD_WIKI_LINK) val wikiLink: String,
    @ColumnInfo(name = CARD_NUMBER) val number: Int,
    @ColumnInfo(name = CARD_IS_CHECK) val isCheck: Boolean
) {

    companion object {
        // TABLE
        const val CARD_TABLE = "card"

        // COLUMN
        const val CARD_NAME = "name"
        const val CARD_FRENCH_NAME = "french_name"
        const val CARD_JAPANESE_NAME = "japanese_name"
        const val CARD_TYPE = "type"
        const val CARD_RARITY = "rarity"
        const val CARD_POKEMON_NUMBER = "pokemon_number"
        const val CARD_PICTURE = "picture"
        const val CARD_ILLUSTRATOR = "illustrator"
        const val CARD_WIKI_LINK = "wiki_link"
        const val CARD_NUMBER = "number"
        const val CARD_IS_CHECK = "is_check"
    }

}
