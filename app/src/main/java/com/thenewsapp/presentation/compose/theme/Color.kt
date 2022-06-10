package com.thenewsapp.presentation.compose.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

// Colors https://materialui.co/colors/
val Purple200 = Color(0xFFCE93D8)
val Purple500 = Color(0xFF9C27B0)
val Purple700 = Color(0xFF7B1FA2)
val Teal200 = Color(0xFF80CBC4)
val Red800 = Color(0xffd00036)
val Red200 = Color(0xfff297a2)

val DarkColorsPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    error = Red200
)

val LightColorsPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    error = Red800
)