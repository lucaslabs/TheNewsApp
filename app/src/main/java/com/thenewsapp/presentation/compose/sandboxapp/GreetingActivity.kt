package com.thenewsapp.presentation.compose.sandboxapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import com.thenewsapp.presentation.compose.theme.BasicsTheme

class GreetingActivity : AppCompatActivity() {

    private val viewModel: GreetingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // States
            val greetingItemStates = viewModel.greetingItemStates
            val counterState by viewModel.counterState

            // Theme
            BasicsTheme {

                // Screen
                GreetingScreen(
                    greetingItemStates = greetingItemStates,
                    onItemClick = { position, greetingItem ->
                        viewModel.onItemClick(position, greetingItem)
                    },
                    counterState = counterState,
                    onCounterClick = { count ->
                        viewModel.onCounterClick(count)
                    },
                )
            }
        }
    }
}