package com.thenewsapp.presentation.compose.model

data class GreetingItem(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
)
