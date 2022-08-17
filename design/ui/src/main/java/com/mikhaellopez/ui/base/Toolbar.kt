package com.mikhaellopez.ui.base

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mikhaellopez.ui.R
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun Toolbar(
    title: String? = null,
    pressOnBack: (() -> Unit)? = null,
    menuActions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = title ?: stringResource(R.string.app_name))
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = menuActions,
        navigationIcon = pressOnBack?.let {
            @Composable {
                IconButton(onClick = { pressOnBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun MenuIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colors.onPrimary
        )
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
        Toolbar(
            menuActions = {
                MenuIcon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Menu Icon for Preview"
                )
            },
            pressOnBack = { /* Pressed on back */ }
        )
    }
}
