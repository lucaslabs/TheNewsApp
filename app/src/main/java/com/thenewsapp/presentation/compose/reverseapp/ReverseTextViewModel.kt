package com.thenewsapp.presentation.compose.reverseapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReverseTextViewModel : ViewModel() {

    private val _reversedText = MutableLiveData("")
    val reversedText: LiveData<String> = _reversedText

    fun onTextValueChange(text: String) {
        _reversedText.value = text.reversed()
    }
}