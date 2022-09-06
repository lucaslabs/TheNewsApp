package com.thenewsapp.presentation.compose.reverseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.thenewsapp.presentation.compose.theme.AppTheme

class ReverseTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                ReverseTextScreen()
            }
        }
    }
}