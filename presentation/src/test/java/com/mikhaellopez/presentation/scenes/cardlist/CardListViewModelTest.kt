package com.mikhaellopez.presentation.scenes.cardlist

import com.mikhaellopez.domain.exception.NoConnectedException
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.model.TestModeEnum
import com.mikhaellopez.domain.usecases.CheckCard
import com.mikhaellopez.domain.usecases.GetListCard
import com.mikhaellopez.domain.usecases.RefreshListCard
import com.mikhaellopez.presentation.exception.ErrorMessageFactoryTest
import com.mikhaellopez.presentation.scenes.base.BaseViewModelTest
import com.mikhaellopez.presentation.scenes.base.PublishSharedFlow
import com.mikhaellopez.ui.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CardListViewModelTest : BaseViewModelTest() {

    // View
    private lateinit var view: CardListView

    // Presenter
    private val getListCard = mock<GetListCard>()
    private val refreshListCard = mock<RefreshListCard>()
    private val checkCard = mock<CheckCard>()
    private val errorMessageFactoryTest = ErrorMessageFactoryTest(mock())
    private val viewModel by lazy {
        CardListViewModel(
            getListCard,
            refreshListCard,
            checkCard,
            TestCoroutineScheduler(),
            errorMessageFactoryTest
        )
    }

    @Before
    fun setup() {
        viewModel.testMode = TestModeEnum.UNIT_TEST
        view = mock {
            on { intentLoadData() } doReturn emptyFlow()
            on { intentRefreshData() } doReturn emptyFlow()
            on { intentErrorRetry() } doReturn emptyFlow()
            on { intentCheck() } doReturn emptyFlow()
            on { uiStateFlow } doReturn MutableStateFlow(UiState.Loading())
        }
    }

    @Test
    fun loadData() = runTest {
        val cardList = mock<List<Card>>()

        whenever(getListCard.execute(Unit)).thenReturn(flowOf(cardList))
        whenever(view.intentLoadData()).thenReturn(flowOf(Unit))

        val (results, job) = launchTest(view)

        // Attach
        viewModel.attach(view)
        await()

        assertEquals(2, results.size)
        assertEquals(UiState.Loading(), results.first())
        assertEquals(UiState.Content(cardList), results.last())

        // Detach
        viewModel.onCleared()
        job.cancel()
    }

    @Test
    fun loadDataWhenUseCaseReturnException() = runTest {
        val noConnectedException = NoConnectedException

        whenever(getListCard.execute(Unit)).thenReturn(flow { throw noConnectedException })
        whenever(view.intentLoadData()).thenReturn(flowOf(Unit))

        val (results, job) = launchTest(view)

        // Attach
        viewModel.attach(view)
        await()

        assertEquals(2, results.size)
        assertEquals(UiState.Loading(), results.first())
        assertEquals(
            UiState.Error(errorMessageFactoryTest.getError(noConnectedException)),
            results.last()
        )

        // Detach
        viewModel.onCleared()
        job.cancel()
    }

    @Test
    fun refresh() = runTest {
        val cardList = mock<List<Card>>()
        val intentRefresh = PublishSharedFlow.create<Unit>()

        whenever(getListCard.execute(Unit)).thenReturn(flowOf(cardList))
        whenever(view.intentLoadData()).thenReturn(flowOf(Unit))
        whenever(refreshListCard.execute(Unit)).thenReturn(flowOf(cardList))
        whenever(view.intentRefreshData()).thenReturn(intentRefresh)

        val (results, job) = launchTest(view)

        // Attach
        viewModel.attach(view)
        await()

        assertEquals(UiState.Loading(), results.first())
        assertEquals(UiState.Content(cardList), results[1])

        intentRefresh.tryEmit(Unit)
        await(1100)

        assertEquals(UiState.Content.createRefresh(cardList), results[2])
        assertEquals(UiState.Content(cardList), results.last())
        assertEquals(4, results.size)

        // Detach
        viewModel.onCleared()
        job.cancel()
    }

    @Test
    fun retry() = runTest {
        val noConnectedException = NoConnectedException
        val cardList = mock<List<Card>>()

        val intentRetry = PublishSharedFlow.create<Unit>()

        whenever(getListCard.execute(Unit)).thenReturn(flow { throw noConnectedException })
        whenever(view.intentLoadData()).thenReturn(flowOf(Unit))
        whenever(view.intentErrorRetry()).thenReturn(intentRetry)

        val (results, job) = launchTest(view)

        // Attach
        viewModel.attach(view)
        await()

        assertEquals(UiState.Loading(), results.first())
        assertEquals(
            UiState.Error(errorMessageFactoryTest.getError(noConnectedException)),
            results[1]
        )

        // Retry
        whenever(getListCard.execute(Unit)).thenReturn(flowOf(cardList))
        intentRetry.tryEmit(Unit)
        await(1100)

        assertEquals(
            UiState.Error.createRetry(errorMessageFactoryTest.getError(noConnectedException)),
            results[2]
        )
        assertEquals(UiState.Content(cardList), results.last())
        assertEquals(4, results.size)

        // Detach
        viewModel.onCleared()
        job.cancel()
    }

    @Test
    fun favoriteCard() = runTest {
        val card = mock<Card>()
        val cardList = mock<List<Card>>()

        val intentCheck = PublishSharedFlow.create<Card>()

        whenever(getListCard.execute(Unit)).thenReturn(flowOf(cardList))
        whenever(view.intentLoadData()).thenReturn(flowOf(Unit))
        whenever(view.intentCheck()).thenReturn(intentCheck)

        val (results, job) = launchTest(view)

        // Attach
        viewModel.attach(view)
        await()

        assertEquals(UiState.Loading(), results.first())
        assertEquals(UiState.Content(cardList), results[1])

        // Favorite
        val favoriteCardList = mock<List<Card>>()
        whenever(checkCard.execute(card)).thenReturn(flowOf(Unit))
        whenever(getListCard.execute(Unit)).thenReturn(flowOf(favoriteCardList))
        intentCheck.tryEmit(card)
        await()

        assertEquals(UiState.Content(favoriteCardList), results.last())
        assertEquals(3, results.size)

        // Detach
        viewModel.onCleared()
        job.cancel()
    }
}
