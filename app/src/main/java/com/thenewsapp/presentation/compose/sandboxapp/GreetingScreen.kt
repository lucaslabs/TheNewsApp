package com.thenewsapp.presentation.compose.sandboxapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thenewsapp.presentation.compose.sandboxapp.model.CounterState
import com.thenewsapp.presentation.compose.sandboxapp.model.GreetingItemState
import com.thenewsapp.presentation.compose.theme.BasicsTheme

@Composable
fun GreetingScreen(
    greetingItemStates: List<GreetingItemState>,
    onItemClick: (Int, GreetingItemState) -> Unit,
    counterState: CounterState,
    onCounterClick: (Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        GreetingList(
            greetingItemStates = greetingItemStates,
            onItemClick = onItemClick,
            modifier = Modifier.weight(1f),
        )
        Counter(
            counterState = counterState,
            onCounterClick = onCounterClick
        )
    }
}

@Composable
fun GreetingList(
    greetingItemStates: List<GreetingItemState>,
    onItemClick: (Int, GreetingItemState) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(items = greetingItemStates) { position, item ->
            GreetingItem(
                position = position,
                greetingItemState = item,
                onItemClick = onItemClick
            )
            Divider(color = Color.Black)
        }
    }
}

// region Components
@Composable
fun GreetingItem(
    position: Int,
    greetingItemState: GreetingItemState,
    onItemClick: (Int, GreetingItemState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        if (greetingItemState.isSelected) MaterialTheme.colors.primary
        else Color.Transparent
    )
    Text(
        text = greetingItemState.name,
        modifier = Modifier
            .padding(12.dp)
            .background(backgroundColor)
            .clickable { onItemClick(position, greetingItemState) }
    )
}

@Composable
fun Counter(
    counterState: CounterState,
    onCounterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val color by animateColorAsState(
        if (counterState.count % 2 == 0) MaterialTheme.colors.primary
        else MaterialTheme.colors.secondary
    )
    Button(
        colors = ButtonDefaults.buttonColors(color),
        onClick = { onCounterClick(counterState.count + 1) }
    ) {
        Text(text = "Clicked ${counterState.count} times")
    }
}
// endregion

@Preview(showBackground = true)
@Composable
fun Preview() {
    BasicsTheme(darkTheme = false) {
        GreetingScreen(
            greetingItemStates = listOf(
                GreetingItemState(1, "Hello #1"),
                GreetingItemState(2, "Hello #2"),
                GreetingItemState(3, "Hello #3")
            ),
            onItemClick = { _, _ -> },
            counterState = CounterState(0),
            onCounterClick = {}
        )
    }
}