package com.mikhaellopez.ui.base

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikhaellopez.ui.R
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun Error(
    errorMessage: String,
    isLoading: Boolean = false,
    retry: (() -> Unit)? = null
) {
    Surface(color = MaterialTheme.colors.background) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 20.dp)
                .semantics { testTag = "error_view" }
        ) {
            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.ErrorOutline,
                    contentDescription = "Cloud error",
                    // tint = colorResource(R.color.icons),
                    modifier = Modifier
                        .size(100.dp)
                        .padding(all = 8.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = stringResource(id = R.string.error_view_oops),
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = errorMessage,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextButton(onClick = { retry?.invoke() }) {
                    Text(stringResource(id = R.string.error_view_retry))
                }
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .semantics { testTag = "error_progress" }
                    )
                } else {
                    Spacer(modifier = Modifier.size(40.dp))
                }
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
        Error(
            errorMessage = "No connection",
            isLoading = true
        )
    }
}
