package com.mikhaellopez.ui.card

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mikhaellopez.domain.model.RarityEnum
import com.mikhaellopez.ui.R
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun CardRarity(
    rarityEnum: RarityEnum,
    size: Dp = 20.dp
) {
    Image(
        painter = painterResource(rarityEnum.toDrawable()),
        contentDescription = "Image Rarity",
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
        modifier = Modifier.size(size)
    )
}

private fun RarityEnum.toDrawable(): Int =
    when (this) {
        RarityEnum.NONE -> R.drawable.none
        RarityEnum.COMMON -> R.drawable.common
        RarityEnum.UNCOMMON -> R.drawable.uncommon
        RarityEnum.RARE, RarityEnum.RARE_HOLO -> R.drawable.rare
        else -> throw RuntimeException("Unknown this type: $this")
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
        CardRarity(RarityEnum.RARE_HOLO)
    }
}