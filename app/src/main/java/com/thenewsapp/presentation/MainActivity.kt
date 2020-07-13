package com.thenewsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.thenewsapp.R
import com.thenewsapp.data.model.News
import com.thenewsapp.presentation.newsdetail.NewsDetailFragment
import com.thenewsapp.presentation.shownews.ShowNewsFragment

class MainActivity : AppCompatActivity(), ShowNewsFragment.ActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            showFragment(ShowNewsFragment.newInstance())
        }
    }

    override fun showNewsDetailView(news: News) {
        showFragment(NewsDetailFragment.newInstance())
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            addToBackStack(fragment.javaClass.simpleName)
            replace(R.id.container, fragment)
        }
    }
}