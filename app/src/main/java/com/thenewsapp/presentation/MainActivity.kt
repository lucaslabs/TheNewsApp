package com.thenewsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thenewsapp.R
import com.thenewsapp.presentation.shownews.ShowNewsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ShowNewsFragment.newInstance())
                .commitNow()
        }
    }
}
