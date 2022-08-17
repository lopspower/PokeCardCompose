package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.PersistenceException
import com.mikhaellopez.domain.extensions.throwIfEmpty
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.repository.CardRepository
import com.mikhaellopez.domain.usecases.base.FlowUseCase
import com.mikhaellopez.domain.usecases.base.Logger
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

/**
 * This class is an implementation of [FlowUseCase] that represents a use case for
 * retrieving a [Card].
 */
@Factory
class GetCacheCard(
    private val cardRepository: CardRepository,
    logger: Logger? = null
) : FlowUseCase<String, Card>(logger) {

    override suspend fun build(param: String): Flow<Card> =
        cardRepository.getCacheCard(param)
            .throwIfEmpty(PersistenceException)
}
