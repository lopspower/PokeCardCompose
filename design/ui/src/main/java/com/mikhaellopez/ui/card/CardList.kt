package com.mikhaellopez.ui.card

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun CardList(
    cards: List<Card>,
    isRefresh: Boolean = false,
    onClick: ((Card) -> Unit)? = null,
    onCheck: ((Card) -> Unit)? = null,
    onRefresh: (() -> Unit)? = null
) {
    var isRefreshing by remember { mutableStateOf(false) }
    isRefreshing = isRefresh
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            isRefreshing = true
            onRefresh?.invoke()
        },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(state, trigger)
        },
    ) {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(all = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(items = cards, key = { it.name }) { card ->
                CardListItem(
                    card = card,
                    onClick = onClick,
                    onCheck = onCheck
                )
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun Preview() {
    BaseTheme {
        CardList(ConstantPreviewCard.CARD_LIST)
    }
}
