package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.NoConnectedException
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
class RefreshCardListTest {

    @Mock
    private lateinit var repository: CardRepository

    // Use Case
    private val useCase by lazy { RefreshListCard(repository) }

    @Test
    fun buildUseCase() = runTest {
        val cardList = mock<List<Card>>()

        whenever(repository.isConnected()).thenReturn(flowOf(true))
        whenever(repository.getListCard()).thenReturn(flowOf(cardList))
        whenever(repository.saveListCard(cardList)).thenReturn(flowOf(Unit))
        whenever(repository.getCacheListCard()).thenReturn(flowOf(cardList))
        whenever(repository.sortListCard(cardList)).thenReturn(flowOf(cardList))

        val flow = useCase.execute(Unit)
        val count = flow.count()
        val first = flow.first()

        assertEquals(count, 1)
        assertEquals(first, cardList)
    }

    @Test
    fun buildUseCaseWithoutNetworkConnection() = runTest {
        whenever(repository.isConnected()).thenReturn(flowOf(false))

        val flow = useCase.execute(Unit)

        flow.catch { cause ->
            assert(cause is NoConnectedException)
        }.collect()
    }
}
