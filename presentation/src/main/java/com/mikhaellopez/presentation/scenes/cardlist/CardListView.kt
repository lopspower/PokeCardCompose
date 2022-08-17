package com.mikhaellopez.presentation.scenes.cardlist

import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.presentation.scenes.base.BaseUiStateView
import kotlinx.coroutines.flow.Flow

interface CardListView : BaseUiStateView {

    fun intentLoadData(): Flow<Unit>

    fun intentRefreshData(): Flow<Unit>

    fun intentErrorRetry(): Flow<Unit>

    fun intentCheck(): Flow<Card>
}
