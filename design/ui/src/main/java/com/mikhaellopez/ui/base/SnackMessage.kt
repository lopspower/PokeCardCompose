package com.mikhaellopez.ui.base

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikhaellopez.ui.theme.BaseTheme
import kotlinx.coroutines.launch

@Composable
fun SnackMessage(
    message: String,
    actionLabel: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short,
    onDismiss: (() -> Unit)? = null,
    onActionPerform: (() -> Unit)? = null
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState
        ) { snackbarData -> SnackbarContent(snackbarData.message, snackbarData.actionLabel) }
        LaunchedEffect(snackbarHostState.currentSnackbarData) {
            scope.launch {
                when (snackbarHostState.showSnackbar(message, actionLabel, duration)) {
                    SnackbarResult.Dismissed -> onDismiss?.invoke()
                    SnackbarResult.ActionPerformed -> onActionPerform?.invoke()
                }
                onDismiss?.invoke()
            }
        }
    }
}

@Composable
private fun SnackbarContent(
    message: String,
    actionLabel: String? = null,
    actionPerformed: (() -> Unit)? = null
) {
    val actionComposable: (@Composable () -> Unit)? =
        if (actionLabel != null) {
            @Composable {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = SnackbarDefaults.primaryActionColor),
                    onClick = { actionPerformed?.invoke() },
                    content = { Text(actionLabel) }
                )
            }
        } else {
            null
        }
    Snackbar(
        modifier = Modifier.padding(12.dp),
        content = { Text(message) },
        action = actionComposable
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
        SnackbarContent(
            message = "Snackbar message",
            actionLabel = "ACTION"
        )
    }
}
