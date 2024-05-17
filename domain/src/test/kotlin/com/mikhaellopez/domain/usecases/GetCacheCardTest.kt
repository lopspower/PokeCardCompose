package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.PersistenceException
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.repository.CardRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetCacheCardTest {

    @Mock
    private lateinit var repository: CardRepository

    // Use Case
    private val useCase by lazy { GetCacheCard(repository) }

    @Test
    fun buildUseCase() = runTest {
        val cardName = "Charizard"
        val cardExpected = mock<Card>()
        whenever(repository.getCacheCard(cardName)).thenReturn(flowOf(cardExpected))

        val flow = useCase.execute(cardName)
        val count = flow.count()
        val first = flow.first()

        assertEquals(count, 1)
        assertEquals(first, cardExpected)
    }

    @Test
    fun buildUseCaseWithPersistenceException() = runTest {
        val cardName = "Charizard"
        whenever(repository.getCacheCard(cardName)).thenReturn(emptyFlow())

        val flow = useCase.execute(cardName)

        flow.catch { cause ->
            assert(cause is PersistenceException)
        }.collect()
    }
}
