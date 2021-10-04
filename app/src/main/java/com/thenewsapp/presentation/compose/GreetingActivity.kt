package com.thenewsapp.presentation.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import com.thenewsapp.presentation.compose.utils.BasicsTheme

class GreetingActivity : AppCompatActivity() {

    private val viewModel: GreetingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // States
            val greetingItems = viewModel.greetingItems
            val counter by viewModel.counter

            // Theme
            BasicsTheme {

                // Screen
                GreetingScreen(
                    greetingItems = greetingItems,
                    onItemClick = { position, greetingItem ->
                        viewModel.onItemClick(position, greetingItem)
                    },
                    counter = counter,
                    onCounterClick = { count ->
                        viewModel.onCounterClick(count)
                    },
                )
            }
        }
    }
}