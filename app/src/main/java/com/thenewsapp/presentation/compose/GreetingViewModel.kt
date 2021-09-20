package com.thenewsapp.presentation.compose

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GreetingViewModel : ViewModel() {

    // Mutable states for Compose instead of LiveData.
    var greetingItems = mutableStateListOf<GreetingItem>()
        private set

    var counter = mutableStateOf(Counter(0))
        private set

    init {
        List(100) { id ->
            greetingItems.add(GreetingItem(id, "Hello #$id", isSelected = false))
        }
    }

    fun onItemClick(position: Int, greetingItem: GreetingItem) {
        greetingItem.isSelected = !greetingItem.isSelected
        greetingItems[position] = greetingItem
    }

    fun onCounterClick(count: Int) {
        counter.value = Counter(count)
    }
}