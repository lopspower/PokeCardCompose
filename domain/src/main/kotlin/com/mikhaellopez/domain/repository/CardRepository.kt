package com.mikhaellopez.domain.repository

import com.mikhaellopez.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    suspend fun isConnected(): Flow<Boolean>

    //region LIST CARD
    suspend fun getListCard(): Flow<List<Card>>

    suspend fun getCacheListCard(): Flow<List<Card>>

    suspend fun sortListCard(list: List<Card>): Flow<List<Card>>

    suspend fun saveListCard(cardList: List<Card>): Flow<Unit>
    //endregion

    //region CARD
    suspend fun getCacheCard(name: String): Flow<Card>

    suspend fun saveCard(card: Card): Flow<Unit>

    suspend fun checkCard(card: Card): Flow<Unit>
    //endregion
}
