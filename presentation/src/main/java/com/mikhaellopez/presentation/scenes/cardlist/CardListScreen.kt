package com.mikhaellopez.presentation.scenes.cardlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.presentation.scenes.base.PublishSharedFlow
import com.mikhaellopez.ui.card.CardGrid
import com.mikhaellopez.ui.card.CardList
import com.mikhaellopez.ui.extensions.clearSnackMessage
import com.mikhaellopez.ui.extensions.filterDefault
import com.mikhaellopez.ui.extensions.toContent
import com.mikhaellopez.ui.state.RenderUiState
import com.mikhaellopez.ui.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.getViewModel

// Intents
private val refreshIntent = PublishSharedFlow.create<Unit>()
private val retryIntent = PublishSharedFlow.create<Unit>()
private val checkIntent = PublishSharedFlow.create<Card>()

@Composable
fun CardListScreen(
    gridMode: Boolean = false,
    openCard: (Card) -> Unit
) {
    val view = remember { createView() }
    val viewModel = getViewModel<CardListViewModel>()
    LaunchedEffect(if (gridMode) "CardGridScreen" else "CardListScreen") { viewModel.attach(view) }

    RenderUiState(
        uiStateFlow = view.uiStateFlow,
        retry = { retryIntent.tryEmit(Unit) }
    ) { uiState ->
        uiState.toContent { cards: List<Card> ->
            if (gridMode) {
                CardGrid(
                    cards = cards,
                    isRefresh = uiState.isRefresh,
                    onClick = {
                        view.uiStateFlow.clearSnackMessage()
                        openCard(it)
                    },
                    onCheck = { checkIntent.tryEmit(it) },
                    onRefresh = { refreshIntent.tryEmit(Unit) }
                )
            } else {
                CardList(
                    cards = cards,
                    isRefresh = uiState.isRefresh,
                    onClick = {
                        view.uiStateFlow.clearSnackMessage()
                        openCard(it)
                    },
                    onCheck = { checkIntent.tryEmit(it) },
                    onRefresh = { refreshIntent.tryEmit(Unit) }
                )
            }
        }
    }
}

private fun createView() = object : CardListView {
    override val uiStateFlow: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading.createDefault())

    override fun intentLoadData(): Flow<Unit> = uiStateFlow.filterDefault().map { }

    override fun intentRefreshData(): Flow<Unit> = refreshIntent

    override fun intentErrorRetry(): Flow<Unit> = retryIntent

    override fun intentCheck(): Flow<Card> = checkIntent
}