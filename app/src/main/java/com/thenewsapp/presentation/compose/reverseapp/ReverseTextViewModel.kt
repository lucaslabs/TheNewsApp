package com.thenewsapp.presentation.compose.reverseapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReverseTextViewModel : ViewModel() {

    private val _textValue = MutableLiveData<String>("")
    val textValue: LiveData<String> = _textValue

    fun onTextValueChange(text: String) {
        _textValue.value = text.reversed()
    }
}