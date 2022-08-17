package com.mikhaellopez.ui.card

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun CardCheck(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheck: (() -> Unit)? = null
) {
    Box(modifier = modifier) {
        // Circle Alpha Check
        Box(
            modifier = Modifier
                .padding(all = 6.dp)
                .size(30.dp)
                .alpha(0.5f)
                .background(MaterialTheme.colors.background, CircleShape)
        )
        // IconButton CheckBox
        Box(
            modifier = Modifier
                .padding(all = 6.dp)
                .size(30.dp)
        ) {
            IconButton(
                modifier = Modifier.fillMaxSize(),
                onClick = { onCheck?.invoke() }
            ) {
                Icon(
                    imageVector = if (isChecked) Icons.Outlined.CheckBox else Icons.Outlined.CheckBoxOutlineBlank,
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = "Check Card"
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
        Surface {
            CardCheck(isChecked = true)
        }
    }
}
