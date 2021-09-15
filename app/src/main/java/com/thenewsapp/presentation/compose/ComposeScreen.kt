package com.thenewsapp.presentation.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScreenContent(names: List<String>) {
    val counterState = remember { mutableStateOf(0) }

    Column {
        names.forEach {
            Greeting(name = it)
        }
        Divider(color = Color.Black, thickness = 2.dp)
        Counter(
            count = counterState.value,
            modifier = Modifier.background(Color.Blue),
            onCounterClick = { newCount ->
                counterState.value = newCount
            }
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name")
}

@Composable
fun Counter(count: Int, modifier: Modifier, onCounterClick: (Int) -> Unit) {
    Toast.makeText(LocalContext.current, "Recomposition $count", Toast.LENGTH_SHORT).show()
    Button(
        modifier = modifier,
        onClick = { onCounterClick(count + 1) }
    ) {
        Text(text = "Clicked $count times")
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ScreenContent(listOf("Lucas", "Gabriela", "Franco"))
}