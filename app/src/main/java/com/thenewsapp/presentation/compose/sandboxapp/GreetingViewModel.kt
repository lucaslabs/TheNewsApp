package com.thenewsapp.presentation.compose.sandboxapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.thenewsapp.presentation.compose.sandboxapp.model.CounterState
import com.thenewsapp.presentation.compose.sandboxapp.model.GreetingItemState

class GreetingViewModel : ViewModel() {

    // Mutable states for Compose instead of LiveData.
    private val _greetingItemStates = mutableStateListOf<GreetingItemState>()
    val greetingItemStates: SnapshotStateList<GreetingItemState> = _greetingItemStates

    private val _counterState = mutableStateOf(CounterState(0))
    val counterState: State<CounterState> = _counterState

    init {
        List(100) { id ->
            _greetingItemStates.add(GreetingItemState(id, "Hello #$id", isSelected = false))
        }
    }

    fun onItemClick(position: Int, greetingItemState: GreetingItemState) {
        greetingItemState.isSelected = !greetingItemState.isSelected
        _greetingItemStates[position] = greetingItemState
    }

    fun onCounterClick(count: Int) {
        _counterState.value = CounterState(count)
    }
}