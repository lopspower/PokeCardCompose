package com.mikhaellopez.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.mikhaellopez.ui.R

private val LightColors = lightColors(
    primary = Color(0xFF644887),
    primaryVariant = Color(0xFF594078),
    onPrimary = Color(0xFFFFFFFF),

    secondary = Color(0xFFf9aa33),
    secondaryVariant = Color(0xFFe0992d),
    onSecondary = Color(0xFF000000),

    background = Color(0xFFEFEFEF),
    onBackground = Color(0xFF222222),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),

    error = Color(0xFFbf324c),
    onError = Color(0xFFFFFFFF)
)

private val DarkColors = lightColors(
    primary = Color(0xFF705097),
    primaryVariant = Color(0xFF644887),
    onPrimary = Color(0xFFFFFFFF),

    secondary = Color(0xFFf9aa33),
    secondaryVariant = Color(0xFFe0992d),
    onSecondary = Color(0xFF000000),

    background = Color(0xFF242424),
    onBackground = Color(0xFFFFFFFF),

    surface = Color(0xFF313131),
    onSurface = Color(0xFFFFFFFF),

    error = Color(0xFFbf324c),
    onError = Color(0xFFFFFFFF)
)

private val Montserrat = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

private val Typography = Typography(
    defaultFontFamily = Montserrat,
    h1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 62.sp // 96.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 50.sp // 60.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp // 48.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp // 34.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp // 24.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold, // FontWeight.Medium,
        fontSize = 18.sp // 20.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.SemiBold, // FontWeight.Medium,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp // 16.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp // 14.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.SemiBold, // FontWeight.Medium,
        fontSize = 12.sp // 14.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp // 12.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp // 10.sp
    )
)

@Composable
fun BaseTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
    // Set Status and Navigation bar color to match the theme
    LocalView.current.also { view ->
        if (!view.isInEditMode) {
            SideEffect {
                (view.context as Activity).window.apply {
                    statusBarColor = colors.primaryVariant.toArgb()
                    navigationBarColor = colors.primaryVariant.toArgb()
                    WindowCompat.getInsetsController(this, view).apply {
                        isAppearanceLightStatusBars = false
                        isAppearanceLightNavigationBars = false
                    }
                }
            }
        }
    }
}
