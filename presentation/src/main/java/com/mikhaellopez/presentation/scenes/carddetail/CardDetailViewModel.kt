package com.mikhaellopez.presentation.scenes.carddetail

import com.mikhaellopez.domain.extensions.share
import com.mikhaellopez.domain.usecases.GetCacheCard
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
class CardDetailViewModel(
    private val getCacheCard: GetCacheCard,
    private val context: CoroutineContext,
    errorMessageFactory: ErrorMessageFactory
) : BaseViewModel<CardDetailView>(context, errorMessageFactory) {

    override fun attach(view: CardDetailView) {
        super.attach(view)
        val loadData = view.intentLoadData().flatMapConcat { loadData(it) }.share(context)
        val retry = view.uiStateFlow.filterError()
            .flatMapLatest { previousErrorMessage ->
                view.intentErrorRetry().flatMapConcat { retry(previousErrorMessage, it) }
            }

        subscribeUiState(view, loadData, retry)
    }

    //region USE CASES TO UI STATE
    private suspend fun loadData(param: String): Flow<UiState> =
        getCacheCard.execute(param).asResult()

    private suspend fun retry(
        previousErrorMessage: String,
        param: String
    ): Flow<UiState> =
        getCacheCard.execute(param)
            .asRetry(previousErrorMessage)
    //endregion
}
