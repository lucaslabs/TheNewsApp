package com.thenewsapp.presentation.compose.model

data class GreetingItemState(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
)
