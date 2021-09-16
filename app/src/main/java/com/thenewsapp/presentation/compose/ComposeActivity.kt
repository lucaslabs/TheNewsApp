package com.thenewsapp.presentation.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color

class ComposeActivity : AppCompatActivity() {

    private val viewModel: GreetingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // States
            val namesState by viewModel.names.observeAsState(listOf())
            val counterState by viewModel.counter.observeAsState(CounterState(0, Color.Blue))

            // Theme
            BasicsTheme {

                // Screen
                GreetingScreen(
                    namesState,
                    counterState,
                    onCounterClick = { count ->
                        viewModel.onCounterClick(count)
                    }
                )
            }
        }
    }
}