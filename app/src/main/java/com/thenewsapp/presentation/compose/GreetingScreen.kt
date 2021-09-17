package com.thenewsapp.presentation.compose

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GreetingScreen(
    greetingItems: List<GreetingItem>,
    onItemClick: (Int, GreetingItem) -> Unit,
    counter: Counter,
    onCounterClick: (Int) -> Unit,
) {
    Toast.makeText(
        LocalContext.current,
        "Recomposing #${counter.count}",
        Toast.LENGTH_SHORT
    ).show()
    Column(modifier = Modifier.fillMaxHeight()) {
        GreetingList(
            greetingItems = greetingItems,
            onItemClick = onItemClick,
            modifier = Modifier.Companion.weight(1f),
        )
        Counter(
            counter = counter,
            onCounterClick = onCounterClick
        )
    }
}

@Composable
fun GreetingList(
    greetingItems: List<GreetingItem>,
    onItemClick: (Int, GreetingItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(items = greetingItems) { position, item ->
            GreetingItem(
                position = position,
                item = item,
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
    item: GreetingItem,
    onItemClick: (Int, GreetingItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        if (item.isSelected) MaterialTheme.colors.primary
        else Color.Transparent
    )
    Text(
        text = item.name,
        modifier = Modifier
            .padding(12.dp)
            .background(backgroundColor)
            .clickable { onItemClick(position, item) }
    )
}

@Composable
fun Counter(
    counter: Counter,
    onCounterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        colors = ButtonDefaults.buttonColors(counter.color),
        onClick = { onCounterClick(counter.count + 1) }
    ) {
        Text(text = "Clicked ${counter.count} times")
    }
}
// endregion

@Preview(showBackground = true)
@Composable
fun Preview() {
    BasicsTheme {
        GreetingScreen(
            greetingItems = listOf(
                GreetingItem(1, "Hello #1"),
                GreetingItem(2, "Hello #2"),
                GreetingItem(3, "Hello #3")
            ),
            onItemClick = { _, _ -> },
            counter = Counter(0, Color.Blue),
            onCounterClick = {}
        )
    }
}