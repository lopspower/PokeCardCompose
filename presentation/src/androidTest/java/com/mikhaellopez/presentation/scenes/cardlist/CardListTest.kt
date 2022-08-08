package com.mikhaellopez.presentation.scenes.cardlist

import androidx.compose.ui.test.junit4.createComposeRule
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.model.RarityEnum
import com.mikhaellopez.domain.model.TypeEnum
import com.mikhaellopez.ui.card.CardList
import com.mikhaellopez.ui.extensions.toContent
import com.mikhaellopez.ui.state.RenderUiState
import com.mikhaellopez.ui.state.UiState
import com.mikhaellopez.ui.theme.BaseTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CardListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val uiStateFlow: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading.createDefault())

    @Before
    fun setup() {
        composeTestRule.setContent {
            BaseTheme {
                RenderUiState(uiStateFlow) { uiState ->
                    uiState.toContent { cards: List<Card> ->
                        CardList(cards)
                    }
                }
            }
        }
    }

    private fun robot(func: CardListRobot.() -> Unit) {
        CardListRobot.robot(composeTestRule, uiStateFlow, func)
    }

    @Test
    fun showData() = runTest {
        val cards = listOf(
            Card(
                "Charizard",
                "Dracaufeu",
                "リザードン",
                TypeEnum.FIRE,
                RarityEnum.RARE_HOLO,
                6,
                "https://lopspower.github.io/poke/img/BaseSet16.webp",
                "Mitsuhiro Arita",
                "https://bulbapedia.bulbagarden.net/wiki/Charizard_(Base_Set_4)",
                16,
                true
            )
        )

        robot {
            loading {
                content()
                progress()
                viewError(false)
                errorProgress(false)
            }
            data(cards) {
                content()
                data(cards[0])
                progress(false)
                viewError(false)
                errorProgress(false)
            }
        }
    }

    @Test
    fun showErrorAndRetry() {
        val myError = "My error"

        robot {
            loading {
                content()
                progress()
            }
            error(myError) {
                viewError()
                textError(myError)
                errorProgress(false)
                content(false)
                progress(false)
            }
            retry(myError) {
                viewError()
                errorProgress()
                textError(myError)
                content(false)
                progress(false)
            }
        }
    }

}