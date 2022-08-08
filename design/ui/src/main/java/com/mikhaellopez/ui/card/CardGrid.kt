package com.mikhaellopez.ui.card

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun CardGrid(
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
        LazyVerticalGrid(
            state = rememberLazyGridState(),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(all = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(items = cards, key = { it.name }) { card ->
                CardGridItem(
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
        CardGrid(ConstantPreviewCard.CARD_LIST)
    }
}