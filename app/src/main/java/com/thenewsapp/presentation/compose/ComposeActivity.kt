package com.thenewsapp.presentation.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class ComposeActivity : AppCompatActivity() {

    private val viewModel: GreetingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // States
            val greetingItems = viewModel.greetingItems
            val counter = viewModel.counter

            // Theme
            BasicsTheme {

                // Screen
                GreetingScreen(
                    greetingItems = greetingItems,
                    onItemClick = { position, greetingItem ->
                        viewModel.onItemClick(position, greetingItem)
                    },
                    counter = counter.value,
                    onCounterClick = { count ->
                        viewModel.onCounterClick(count)
                    },
                )
            }
        }
    }
}