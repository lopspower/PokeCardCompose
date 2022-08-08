package com.mikhaellopez.ui.state

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.mikhaellopez.ui.base.Error
import com.mikhaellopez.ui.base.Progress
import com.mikhaellopez.ui.base.SnackMessage
import com.mikhaellopez.ui.base.Toolbar
import com.mikhaellopez.ui.extensions.clearSnackMessage
import com.mikhaellopez.ui.theme.BaseTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun RenderUiState(
    uiStateFlow: MutableStateFlow<UiState>,
    topAppBarTitle: String? = null,
    pressOnBack: (() -> Unit)? = null,
    retry: (() -> Unit)? = null,
    menuActions: @Composable RowScope.() -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable ((UiState.Content<*>) -> Unit)? = null
) {
    val uiState = uiStateFlow.collectAsState().value
    Scaffold(
        topBar = {
            Toolbar(
                title = topAppBarTitle,
                pressOnBack = pressOnBack,
                menuActions = menuActions
            )
        },
        content = { innerPadding ->
            when (uiState) {
                is UiState.Loading -> Progress()
                is UiState.Error -> Error(
                    errorMessage = uiState.message,
                    isLoading = uiState.isLoading,
                    retry = retry
                )
                is UiState.Content<*> -> {
                    Box(modifier = Modifier.padding(innerPadding)) {
                        content?.invoke(uiState)
                        uiState.snackMessage?.let { message ->
                            SnackMessage(message = message, onDismiss = {
                                uiStateFlow.clearSnackMessage()
                            })
                        }
                    }
                }
            }
        },
        floatingActionButton = floatingActionButton,
        modifier = Modifier.semantics { testTag = "content_view" }
    )
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
        RenderUiState(MutableStateFlow(UiState.Content("Test")))
    }
}