package com.mikhaellopez.presentation.scenes.carddetail

import com.mikhaellopez.domain.exception.NoConnectedException
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.model.TestModeEnum
import com.mikhaellopez.domain.usecases.GetCacheCard
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
class CardDetailViewModelTest : BaseViewModelTest() {

    // View
    private lateinit var view: CardDetailView

    // Presenter
    private val getCacheCard = mock<GetCacheCard>()
    private val errorMessageFactoryTest = ErrorMessageFactoryTest(mock())
    private val viewModel by lazy {
        CardDetailViewModel(
            getCacheCard,
            TestCoroutineScheduler(),
            errorMessageFactoryTest
        )
    }

    // Properties
    private val cardName = "Charizard"

    @Before
    fun setup() {
        viewModel.testMode = TestModeEnum.UNIT_TEST
        view = mock {
            on { intentLoadData() } doReturn emptyFlow()
            on { intentErrorRetry() } doReturn emptyFlow()
            on { uiStateFlow } doReturn MutableStateFlow(UiState.Loading())
        }
    }

    @Test
    fun loadData() = runTest {
        val param = cardName
        val card = mock<Card>()

        whenever(getCacheCard.execute(param)).thenReturn(flowOf(card))
        whenever(view.intentLoadData()).thenReturn(flowOf(param))

        val (results, job) = launchTest(view)

        // Attach
        viewModel.attach(view)
        await()

        assertEquals(2, results.size)
        assertEquals(UiState.Loading(), results.first())
        assertEquals(UiState.Content(card), results.last())

        // Detach
        viewModel.onCleared()
        job.cancel()
    }

    @Test
    fun loadDataWhenUseCaseReturnException() = runTest {
        val param = cardName
        val noConnectedException = NoConnectedException

        whenever(getCacheCard.execute(cardName)).thenReturn(flow { throw noConnectedException })
        whenever(view.intentLoadData()).thenReturn(flowOf(param))

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
    fun retry() = runTest {
        val param = cardName
        val noConnectedException = NoConnectedException
        val card = mock<Card>()

        val intentRetry = PublishSharedFlow.create<String>()

        whenever(getCacheCard.execute(param)).thenReturn(flow { throw noConnectedException })
        whenever(view.intentLoadData()).thenReturn(flowOf(param))
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
        whenever(getCacheCard.execute(param)).thenReturn(flowOf(card))
        intentRetry.tryEmit(param)
        await(1100)

        assertEquals(
            UiState.Error.createRetry(errorMessageFactoryTest.getError(noConnectedException)),
            results[2]
        )
        assertEquals(UiState.Content(card), results.last())
        assertEquals(4, results.size)

        // Detach
        viewModel.onCleared()
        job.cancel()
    }

}
