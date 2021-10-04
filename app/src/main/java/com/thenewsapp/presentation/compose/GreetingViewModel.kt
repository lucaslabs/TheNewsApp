package com.thenewsapp.presentation.compose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class GreetingViewModel : ViewModel() {

    // Mutable states for Compose instead of LiveData.
    private val _greetingItems = mutableStateListOf<GreetingItem>()
    val greetingItems: SnapshotStateList<GreetingItem> = _greetingItems

    private val _counter = mutableStateOf(Counter(0))
    val counter: State<Counter> = _counter

    init {
        List(100) { id ->
            greetingItems.add(GreetingItem(id, "Hello #$id", isSelected = false))
        }
    }

    fun onItemClick(position: Int, greetingItem: GreetingItem) {
        greetingItem.isSelected = !greetingItem.isSelected
        _greetingItems[position] = greetingItem
    }

    fun onCounterClick(count: Int) {
        _counter.value = Counter(count)
    }
}