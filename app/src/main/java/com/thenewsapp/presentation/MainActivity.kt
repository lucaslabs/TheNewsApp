package com.thenewsapp.presentation

import android.os.Bundle
import android.transition.Explode
import android.transition.TransitionSet
import android.widget.ImageView
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

    override fun showNewsDetailView(news: News, sharedImageView: ImageView) {
        val newsDetailFragment = NewsDetailFragment.newInstance()
        newsDetailFragment.sharedElementEnterTransition = TransitionSet().addTransition(Explode())

        showFragment(newsDetailFragment, sharedImageView)
    }

    private fun showFragment(fragment: Fragment, sharedImageView: ImageView? = null) {
        supportFragmentManager.commit {
            sharedImageView?.let { image ->
                addSharedElement(image, image.transitionName)
            }
            addToBackStack(fragment.javaClass.simpleName)
            replace(R.id.container, fragment)
        }
    }
}