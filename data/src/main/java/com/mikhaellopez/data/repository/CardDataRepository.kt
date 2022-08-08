package com.mikhaellopez.data.repository

import com.mikhaellopez.data.mapper.CardMapper
import com.mikhaellopez.data.net.NetworkChecker
import com.mikhaellopez.data.net.api.PokeApi
import com.mikhaellopez.data.persistence.processor.CardProcessor
import com.mikhaellopez.domain.extensions.defaultIfEmpty
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.repository.CardRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

/**
 * [CardRepository] for retrieving card data.
 */
@OptIn(FlowPreview::class)
class CardDataRepository(
    private val pokeApi: PokeApi,
    private val cardMapper: CardMapper,
    private val cardProcessor: CardProcessor,
    private val networkChecker: NetworkChecker
) : CardRepository {

    override suspend fun isConnected(): Flow<Boolean> =
        flow { emit(networkChecker.isConnected) }

    //region LIST CARD
    override suspend fun getListCard(): Flow<List<Card>> =
        flow { emit(pokeApi.getCardList()) }
            .map { cardMapper.transformCollection(it) }

    override suspend fun getCacheListCard(): Flow<List<Card>> =
        cardProcessor.getAll()
            .map { cardMapper.transform(it) }

    override suspend fun sortListCard(list: List<Card>): Flow<List<Card>> =
        flowOf(list.sortedBy { it.number })

    override suspend fun saveListCard(cardList: List<Card>): Flow<Unit> =
        combine(cardList.map { saveCard(it) }) { it.toList() }
    //endregion

    //region CARD
    override suspend fun getCacheCard(name: String): Flow<Card> =
        cardProcessor.get(name)
            .map { cardEntity ->
                cardEntity?.let { cardMapper.transform(it) }
                    ?: throw IllegalStateException("CardEntity not found")
            }

    override suspend fun saveCard(card: Card): Flow<Unit> =
        cardProcessor.get(card.name)
            .defaultIfEmpty(cardMapper.transformToEntity(card))
            .flatMapConcat {
                cardProcessor.insert(
                    cardMapper.transformToEntity(
                        card.copy(isCheck = it.isCheck)
                    )
                )
            }

    override suspend fun checkCard(card: Card): Flow<Unit> =
        cardProcessor.updateIsCheck(card.name, !card.isCheck)
    //endregion

}
