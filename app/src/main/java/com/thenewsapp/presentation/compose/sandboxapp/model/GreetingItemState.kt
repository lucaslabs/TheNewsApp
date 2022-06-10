package com.thenewsapp.presentation.compose.sandboxapp.model

data class GreetingItemState(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
)
