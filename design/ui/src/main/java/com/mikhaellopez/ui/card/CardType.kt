package com.mikhaellopez.ui.card

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mikhaellopez.domain.model.TypeEnum
import com.mikhaellopez.ui.R
import com.mikhaellopez.ui.theme.BaseTheme

@Composable
fun CardType(
    typeEnum: TypeEnum,
    size: Dp = 20.dp
) {
    typeEnum.toDrawable()?.let { typeDrawable ->
        Image(
            painter = painterResource(typeDrawable),
            contentDescription = "Image Type",
            modifier = Modifier.size(size)
        )
    }
}

private fun TypeEnum.toDrawable(): Int? =
    when (this) {
        TypeEnum.GRASS -> R.drawable.grass
        TypeEnum.FIRE -> R.drawable.fire
        TypeEnum.WATER -> R.drawable.water
        TypeEnum.LIGHTNING -> R.drawable.lightning
        TypeEnum.PSYCHIC -> R.drawable.psychic
        TypeEnum.FIGHTING -> R.drawable.fighting
        TypeEnum.COLORLESS -> R.drawable.colorless
        TypeEnum.TRAINER, TypeEnum.ENERGY -> null
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
        CardType(TypeEnum.FIRE)
    }
}