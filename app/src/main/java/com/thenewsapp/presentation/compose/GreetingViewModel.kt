package com.thenewsapp.presentation.compose

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GreetingViewModel : ViewModel() {

    var greetingItems = mutableStateListOf<GreetingItem>()
        private set

    private val _counter = MutableLiveData<Counter>()
    val counter: LiveData<Counter> = _counter

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
        _counter.value = Counter(count, color)
    }
}