package com.thenewsapp.presentation.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GreetingScreen(
    namesState: List<String>,
    counterState: CounterState,
    onCounterClick: (Int) -> Unit
) {
    Toast.makeText(
        LocalContext.current,
        "Recomposing #${counterState.count}",
        Toast.LENGTH_SHORT
    ).show()

    Column(modifier = Modifier.fillMaxHeight()) {
        Column(modifier = Modifier.weight(1f)) {
            namesState.forEach {
                Greeting(name = it)
            }
            Divider(color = Color.Black, thickness = 2.dp)
        }
        Counter(
            state = counterState,
            onCounterClick = onCounterClick
        )
    }
}

// region Components
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name")
}

@Composable
fun Counter(state: CounterState, onCounterClick: (Int) -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(state.color),
        onClick = { onCounterClick(state.count + 1) }
    ) {
        Text(text = "Clicked ${state.count} times")
    }
}
// endregion

@Preview(showBackground = true)
@Composable
fun Preview() {
    GreetingScreen(
        listOf("Lucas", "Gabriela", "Franco"),
        CounterState(0, Color.Blue),
        {}
    )
}