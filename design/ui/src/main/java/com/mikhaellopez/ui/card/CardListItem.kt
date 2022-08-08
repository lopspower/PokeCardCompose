package com.mikhaellopez.ui.card

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.ui.R
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun CardListItem(
    card: Card,
    onClick: ((Card) -> Unit)? = null,
    onCheck: ((Card) -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Box(modifier = Modifier.clickable { onClick?.invoke(card) }) {
            Row(modifier = Modifier
                .padding(all = 8.dp)
                .height(90.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(card.picture),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .aspectRatio(350f / 495f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colors.background),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Card ${card.name}"
                )
                Spacer(modifier = Modifier.size(6.dp))
                Column(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = card.name,
                            maxLines = 1,
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        CardRarity(rarityEnum = card.rarity, size = 16.dp)
                    }
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = "Pokedex N° ${card.pokemonNumber}",
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body2
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.draw),
                            contentDescription = "Illustrator",
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(Modifier.size(6.dp))
                        Text(
                            text = card.illustrator,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.size(3.dp))
                    CardType(typeEnum = card.type)
                    Spacer(Modifier.weight(1f))
                    CardCheck(
                        isChecked = card.isCheck,
                        onCheck = { onCheck?.invoke(card) }
                    )
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
        CardListItem(ConstantPreviewCard.CARD)
    }
}