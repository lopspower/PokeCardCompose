package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.NoConnectedException
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.repository.CardRepository
import com.mikhaellopez.domain.usecases.base.FlowUseCase
import com.mikhaellopez.domain.usecases.base.Logger
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

/**
 * This class is an implementation of [FlowUseCase] that represents a use case for
 * retrieving a collection of all [Card].
 */
@OptIn(FlowPreview::class)
@Factory
class GetListCard(
    private val cardRepository: CardRepository,
    logger: Logger? = null
) : FlowUseCase<Unit, List<Card>>(logger) {

    override suspend fun build(param: Unit): Flow<List<Card>> {
        val getCacheListCard = cardRepository.getCacheListCard()
            .flatMapConcat { cardRepository.sortListCard(it) }

        val cache = getCacheListCard
            .map { it.ifEmpty { throw NoConnectedException } }

        val net = cardRepository.getListCard()
            .flatMapConcat { cardRepository.saveListCard(it) }
            .flatMapConcat { getCacheListCard }

        return cardRepository.isConnected()
            .flatMapConcat { isConnected ->
                if (isConnected) net
                else cache
            }
    }

}
