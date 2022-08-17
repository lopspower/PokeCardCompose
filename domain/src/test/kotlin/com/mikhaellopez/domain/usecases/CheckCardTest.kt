package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.PersistenceException
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.repository.CardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CheckCardTest {

    @Mock
    private lateinit var repository: CardRepository

    // Use Case
    private val useCase by lazy { CheckCard(repository) }

    @Test
    fun buildUseCase() = runTest {
        val card = mock<Card>()
        whenever(repository.checkCard(card)).thenReturn(flowOf(Unit))

        val flow = useCase.execute(card)
        val count = flow.count()
        val first = flow.first()

        assertEquals(count, 1)
        assertEquals(first, Unit)
    }

    @Test
    fun buildUseCaseWithPersistenceException() = runTest {
        val card = mock<Card>()
        whenever(repository.checkCard(card)).thenReturn(flow { throw PersistenceException })

        val flow = useCase.execute(card)

        flow.catch { cause ->
            assert(cause is PersistenceException)
        }.collect()
    }
}
