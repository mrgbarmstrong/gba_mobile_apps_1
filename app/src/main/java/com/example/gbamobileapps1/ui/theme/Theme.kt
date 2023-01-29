package com.example.gbamobileapps1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = TechNavy,
    primaryVariant = DarkGrey,
    secondary = TechGold
)

private val LightColorPalette = lightColors(
    primary = TechGold,
    primaryVariant = BuzzGold,
    secondary = TechNavy,
    background = LightGrey,
    onPrimary = White
)

@Composable
fun GBAMobileApps1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}