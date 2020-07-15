package com.thenewsapp.presentation

import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
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
        val newsDetailFragment = createTransitionsForNewsDetailFragment()
        showFragment(newsDetailFragment)
    }

    private fun showFragment(fragment: Fragment, sharedImageView: ImageView? = null) {
        supportFragmentManager.commit {

            sharedImageView?.let { image ->
                ViewCompat.getTransitionName(image)?.let { transitionName ->
                    addSharedElement(image, transitionName)
                }
            }

            addToBackStack(fragment.javaClass.simpleName)
            replace(R.id.container, fragment)
        }
    }

    private fun createTransitionsForNewsDetailFragment(): NewsDetailFragment {
        val changeImageTransformTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.change_image_transform)

        val explodeTransition =
            TransitionInflater.from(this).inflateTransition(android.R.transition.explode)

        val newsDetailFragment = NewsDetailFragment.newInstance()
        newsDetailFragment.sharedElementEnterTransition = changeImageTransformTransition
        newsDetailFragment.enterTransition = explodeTransition
        return newsDetailFragment
    }
}