package com.mikhaellopez.presentation.scenes.cardlist

import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.usecases.CheckCard
import com.mikhaellopez.domain.usecases.GetListCard
import com.mikhaellopez.domain.usecases.RefreshListCard
import com.mikhaellopez.presentation.exception.ErrorMessageFactory
import com.mikhaellopez.presentation.scenes.base.BaseViewModel
import com.mikhaellopez.ui.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlin.coroutines.CoroutineContext

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class CardListViewModel(
    private val getListCard: GetListCard,
    private val refreshListCard: RefreshListCard,
    private val checkCard: CheckCard,
    context: CoroutineContext,
    errorMessageFactory: ErrorMessageFactory
) : BaseViewModel<CardListView>(context, errorMessageFactory) {

    override fun attach(view: CardListView) {
        super.attach(view)
        val loadData = view.intentLoadData().flatMapConcat { loadData() }
        val refresh = view.uiStateFlow.filterData<List<Card>>()
            .flatMapLatest { previousData: List<Card> ->
                view.intentRefreshData()
                    .flatMapConcat { refresh(previousData) }
            }
        val retry = view.uiStateFlow.filterError()
            .flatMapLatest { previousErrorMessage ->
                view.intentErrorRetry().flatMapConcat { retry(previousErrorMessage) }
            }
        val check = view.intentCheck()
            .flatMapConcat { check(it) }

        subscribeUiState(view, loadData, retry, refresh, check)
    }

    //region USE CASES TO UI STATE
    private suspend fun loadData(): Flow<UiState> =
        getListCard.execute(Unit).asResult()

    private suspend fun refresh(
        previousData: List<Card>
    ): Flow<UiState> =
        refreshListCard.execute(Unit).asRefresh(previousData)

    private suspend fun retry(
        previousErrorMessage: String
    ): Flow<UiState> =
        getListCard.execute(Unit).asRetry(previousErrorMessage)

    private suspend fun check(card: Card): Flow<UiState> =
        checkCard.execute(card)
            .flatMapConcat { getListCard.execute(Unit) }
            .asResult(withLoading = false)
    //endregion
}
