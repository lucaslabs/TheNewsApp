package com.thenewsapp.presentation.compose.reverseapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReverseTextViewModel : ViewModel() {

    private val _reversedText = MutableLiveData<String>()
    val reversedText: LiveData<String> = _reversedText

    private val _inputTextList = MutableLiveData<List<String>>()
    val inputTextList: LiveData<List<String>> = _inputTextList

    private val textList = mutableListOf<String>()

    fun onTextValueChange(text: String) {
        textList.add(text)
        _inputTextList.value = textList

        _reversedText.value = text.reversed()
    }
}