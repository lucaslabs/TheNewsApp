package com.thenewsapp.presentation.compose

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
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
        NameList(namesState, modifier = Modifier.Companion.weight(1f))
        Counter(
            state = counterState,
            onCounterClick = onCounterClick
        )
    }
}

@Composable
fun NameList(namesState: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(namesState) { name ->
            Greeting(name = name)
            Divider(color = Color.Black)
        }
    }
}

// region Components
@Composable
fun Greeting(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        if (isSelected) MaterialTheme.colors.primary
        else Color.Transparent
    )

    Text(
        text = name,
        modifier = Modifier
            .padding(12.dp)
            .background(backgroundColor)
            .clickable { isSelected = !isSelected }
    )
}

@Composable
fun Counter(state: CounterState, modifier: Modifier = Modifier, onCounterClick: (Int) -> Unit) {
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
    BasicsTheme {
        GreetingScreen(
            listOf("Hello #1", "Hello #2", "Hello #3"),
            CounterState(0, Color.Blue),
            {}
        )
    }
}