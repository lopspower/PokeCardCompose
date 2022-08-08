package com.mikhaellopez.presentation.scenes.cardlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.presentation.scenes.base.Robot
import com.mikhaellopez.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow

class CardListRobot(
    private val composeTestRule: ComposeContentTestRule,
    uiState: MutableStateFlow<UiState>
) : Robot(uiState) {

    companion object {
        fun robot(
            composeTestRule: ComposeContentTestRule,
            uiState: MutableStateFlow<UiState>,
            func: CardListRobot.() -> Unit
        ) {
            CardListRobot(composeTestRule, uiState).apply { func() }
        }
    }

    //region UiState
    fun loading(func: CardListRobot.() -> Unit) {
        setUiState(this, UiState.Loading(), func)
    }

    fun data(data: List<Card>, func: CardListRobot.() -> Unit) {
        setUiState(this, UiState.Content(data), func)
    }

    fun error(error: String, func: CardListRobot.() -> Unit) {
        setUiState(this, UiState.Error(error), func)
    }

    fun retry(error: String, func: CardListRobot.() -> Unit) {
        setUiState(this, UiState.Error.createRetry(error), func)
    }
    //endregion

    //region View
    fun content(displayed: Boolean = true) {
        if (displayed) {
            composeTestRule
                .onNodeWithTag(testTag = "content_view")
                .assertIsDisplayed()
        }
    }

    fun progress(displayed: Boolean = true) {
        if (displayed) {
            composeTestRule
                .onNodeWithTag(testTag = "main_progress")
                .assertIsDisplayed()
        }
    }

    fun viewError(displayed: Boolean = true) {
        if (displayed) {
            composeTestRule
                .onNodeWithTag(testTag = "error_view")
                .assertIsDisplayed()
        }
    }

    fun errorProgress(displayed: Boolean = true) {
        if (displayed) {
            composeTestRule
                .onNodeWithTag(testTag = "error_progress")
                .assertIsDisplayed()
        }
    }

    fun textError(text: String) {
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    fun data(item: Card) {
        composeTestRule.onNodeWithText(item.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(item.illustrator).assertIsDisplayed()
    }
    //endregion

}