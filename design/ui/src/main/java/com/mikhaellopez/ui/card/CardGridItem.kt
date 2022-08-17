package com.mikhaellopez.ui.card

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun CardGridItem(
    card: Card,
    onClick: ((Card) -> Unit)? = null,
    onCheck: ((Card) -> Unit)? = null
) {
    Card(
        modifier = Modifier.aspectRatio(350f / 495f),
        shape = RoundedCornerShape(6.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Box(modifier = Modifier.clickable { onClick?.invoke(card) }) {
            Image(
                painter = rememberAsyncImagePainter(card.picture),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = "Card ${card.name}"
            )
            CardCheck(
                isChecked = card.isCheck,
                onCheck = { onCheck?.invoke(card) },
                modifier = Modifier.align(Alignment.BottomEnd)
            )
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
        CardGridItem(ConstantPreviewCard.CARD)
    }
}
