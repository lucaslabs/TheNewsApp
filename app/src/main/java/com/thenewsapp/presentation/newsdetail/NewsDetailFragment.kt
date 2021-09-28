package com.thenewsapp.presentation.newsdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.toSpannable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.thenewsapp.data.model.News
import com.thenewsapp.databinding.NewsDetailFragmentBinding
import com.thenewsapp.presentation.loadUrl
import com.thenewsapp.presentation.shownews.SharedNewsViewModel

class NewsDetailFragment : Fragment() {

    private val viewModel: SharedNewsViewModel by activityViewModels()

    private lateinit var binding: NewsDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = NewsDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSelectedNews()
    }

    private fun getSelectedNews() {
        viewModel.getSelectedNews()?.let { news ->
            with(binding) {
                ivNews.loadUrl(news.urlToImage)
                tvTitle.text = news.title
                tvAuthor.text = "${news.author} @ "
                tvSource.text = news.source.name
                tvDescription.text = news.description

                tvUrl.text = getNewsUrl(news)
                tvUrl.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    private fun getNewsUrl(news: News): Spannable {
        val span = buildSpannedString {
            bold {
                append("Read more at ")
            }
            append(news.url)
        }.toSpannable()

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(news.url)))
            }
        }
        span.setSpan(clickableSpan, 13, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return span
    }
}