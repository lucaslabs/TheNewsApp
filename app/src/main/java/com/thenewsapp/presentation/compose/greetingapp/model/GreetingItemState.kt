package com.thenewsapp.presentation.compose.greetingapp.model

data class GreetingItemState(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
)
