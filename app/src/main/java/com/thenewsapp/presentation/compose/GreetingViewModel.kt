package com.thenewsapp.presentation.compose

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GreetingViewModel : ViewModel() {

    private val _names = MutableLiveData<List<String>>()
    val names: LiveData<List<String>> = _names

    private val _counter = MutableLiveData<CounterState>()
    val counter: LiveData<CounterState> = _counter

    init {
        _names.value = listOf("Lucas", "Gabriela", "Franco")
    }

    fun onCounterClick(count: Int) {
        val color = if (count % 2 == 0) Color.Blue else Color.Red
        _counter.value = CounterState(count, color)
    }
}