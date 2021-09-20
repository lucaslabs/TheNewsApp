package com.thenewsapp.presentation.compose

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class GreetingViewModel : ViewModel() {

    // Mutable states for Compose instead of LiveData.
    var greetingItems = mutableStateListOf<GreetingItem>()
        private set

    var counter = mutableStateOf(Counter(0, Color.Blue))
        private set

    init {
        List(1000) { id ->
            greetingItems.add(GreetingItem(id, "Hello #$id", isSelected = false))
        }
    }

    fun onItemClick(position: Int, greetingItem: GreetingItem) {
        greetingItem.isSelected = !greetingItem.isSelected
        greetingItems[position] = greetingItem
    }

    fun onCounterClick(count: Int) {
        val color = if (count % 2 == 0) Color.Blue else Color.Red
        counter.value = Counter(count, color)
    }
}