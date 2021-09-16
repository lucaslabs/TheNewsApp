package com.thenewsapp.presentation.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BasicsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(colors = colors) {
        content()
    }
}

private val DarkColors = darkColors(
    primary = Colors.purple200,
    primaryVariant = Colors.purple700,
    secondary = Colors.teal200
)

private val LightColors = lightColors(
    primary = Colors.purple500,
    primaryVariant = Colors.purple700,
    secondary = Colors.teal200
)

class Colors {
    companion object {
        // Colors https://materialui.co/colors/
        val purple200 = Color(0xFFCE93D8)
        val purple500 = Color(0xFF9C27B0)
        val purple700 = Color(0xFF7B1FA2)
        val teal200 = Color(0xFF80CBC4)
    }
}