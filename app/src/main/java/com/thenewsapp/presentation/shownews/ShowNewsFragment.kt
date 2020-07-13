package com.thenewsapp.presentation.shownews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.thenewsapp.data.DependencyProvider
import com.thenewsapp.data.NewsService
import com.thenewsapp.data.model.News
import com.thenewsapp.data.net.model.Resource
import com.thenewsapp.databinding.ShowNewsFragmentBinding

class ShowNewsFragment : Fragment(), ShowNewsAdapter.NewsSelectedListener {

    private lateinit var binding: ShowNewsFragmentBinding

    private lateinit var actionListener: ActionListener

    private val newsService = DependencyProvider.provideService(NewsService::class.java)

    private val viewModelFactory = SharedNewsViewModel.Factory(newsService)

    private val viewModel: SharedNewsViewModel by activityViewModels { viewModelFactory }

    private val QUERY = "android"

    companion object {
        fun newInstance() = ShowNewsFragment()
    }

    interface ActionListener {
        fun showNewsDetailView(news: News)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actionListener = context as ActionListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement ActionListener!")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShowNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadNews()
    }

    override fun onNewsSelected(news: News) {
        actionListener.showNewsDetailView(news)
        viewModel.setSelectedNews(news)
    }

    private fun loadNews() {
        viewModel.getNews(QUERY).observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    showNews(resource.data)
                }
                is Resource.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    showError(resource.throwable.message)
                }
            }
        })
    }

    private fun showNews(news: List<News>) = with(binding) {
        rvNews.layoutManager = LinearLayoutManager(activity)

        // TODO Use Concat Adapter

        val adapter = ShowNewsAdapter(news, this@ShowNewsFragment)
        rvNews.adapter = adapter
    }

    private fun showError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}