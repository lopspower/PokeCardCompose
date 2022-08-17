package com.mikhaellopez.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.mikhaellopez.data.persistence.dao.base.BaseDao
import com.mikhaellopez.data.persistence.entity.CardEntity
import com.mikhaellopez.data.persistence.entity.CardEntity.Companion.CARD_IS_CHECK
import com.mikhaellopez.data.persistence.entity.CardEntity.Companion.CARD_NAME
import com.mikhaellopez.data.persistence.entity.CardEntity.Companion.CARD_TABLE

@Dao
abstract class CardDao : BaseDao<CardEntity> {

    /**
     * Select a card with his name
     * @param name the name of the card
     * @return A [CardEntity]
     */
    @Query("SELECT * FROM $CARD_TABLE WHERE $CARD_NAME = :name")
    abstract suspend fun get(name: String): CardEntity?

    /**
     * Select all cards
     * @return A list of [CardEntity] of all the cards in the table
     */
    @Query("SELECT * FROM $CARD_TABLE")
    abstract suspend fun getAll(): List<CardEntity>

    /**
     * Updates if a card is in favorites or not
     * @param name the name of the card
     * @param isCheck if card is check or not
     * @return A number of card updated. This should always be `1`
     */
    @Query("UPDATE $CARD_TABLE SET $CARD_IS_CHECK = :isCheck WHERE $CARD_NAME = :name")
    abstract suspend fun updateIsCheck(name: String, isCheck: Boolean): Int
}
