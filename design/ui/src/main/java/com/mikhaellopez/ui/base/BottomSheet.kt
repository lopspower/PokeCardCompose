package com.mikhaellopez.ui.base

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun BottomSheet(
    onDismiss: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Box(modifier = Modifier.padding(top = 35.dp)) {
            content()
        }
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 5.dp,
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.TopCenter)
                .width(70.dp)
                .clip(RoundedCornerShape(50.dp))
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(22.dp)
        ) {
            Button(
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier.size(28.dp),
                onClick = { onDismiss?.invoke() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = Color.White,
                    contentDescription = "Close Bottom Sheet",
                    modifier = Modifier.size(16.dp)
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
        BottomSheet {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(
                    text = "Preview Bottom Sheet Content",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
