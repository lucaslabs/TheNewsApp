package com.thenewsapp.presentation

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.thenewsapp.R
import com.thenewsapp.data.model.News
import com.thenewsapp.presentation.shownews.ShowNewsFragment
import com.thenewsapp.presentation.shownews.ShowNewsFragmentDirections

class MainActivity : AppCompatActivity(), ShowNewsFragment.ActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun showNewsDetailView(news: News, sharedImageView: ImageView) {
        val action = ShowNewsFragmentDirections.actionShowNewsFragmentToNewsDetailFragment()
        findNavController(R.id.nav_host_fragment).navigate(action)
    }
}