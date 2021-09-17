package com.thenewsapp.presentation.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

class ComposeActivity : AppCompatActivity() {

    private val viewModel: GreetingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // States
            val greetingItems = remember { viewModel.greetingItems }
            val counterState by viewModel.counter.observeAsState(Counter(0, Color.Blue))

            // Theme
            BasicsTheme {

                // Screen
                GreetingScreen(
                    greetingItems,
                    onItemClick = { position, greetingItem ->
                        viewModel.onItemClick(position, greetingItem)
                    },
                    counterState,
                    onCounterClick = { count ->
                        viewModel.onCounterClick(count)
                    }
                )
            }
        }
    }
}