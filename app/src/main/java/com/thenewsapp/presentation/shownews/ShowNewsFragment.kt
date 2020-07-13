package com.thenewsapp.presentation.shownews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.thenewsapp.R
import com.thenewsapp.data.DependencyProvider
import com.thenewsapp.data.NewsService
import com.thenewsapp.data.model.News
import com.thenewsapp.data.net.model.Resource
import com.thenewsapp.databinding.ShowNewsFragmentBinding
import com.thenewsapp.presentation.hide
import com.thenewsapp.presentation.show

class ShowNewsFragment : Fragment(), ShowNewsAdapter.NewsSelectedListener {

    private lateinit var binding: ShowNewsFragmentBinding

    private lateinit var actionListener: ActionListener

    private lateinit var adapter: ShowNewsAdapter

    private val newsService = DependencyProvider.provideService(NewsService::class.java)

    private val viewModelFactory = SharedNewsViewModel.Factory(newsService)

    private val viewModel: SharedNewsViewModel by activityViewModels { viewModelFactory }

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

        setupSearchView()
        setupAdapter()
        getNews()
    }

    override fun onNewsSelected(news: News) {
        actionListener.showNewsDetailView(news)
        viewModel.setSelectedNews(news)
    }

    private fun setupSearchView() {
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchNews(it)
                } ?: run {
                    Toast.makeText(
                        activity, getString(R.string.enter_search_criteria), Toast.LENGTH_SHORT
                    ).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupAdapter() = with(binding) {
        rvNews.layoutManager = LinearLayoutManager(activity)

        // TODO Use Concat Adapter

        adapter = ShowNewsAdapter(arrayListOf(), this@ShowNewsFragment)
        rvNews.adapter = adapter
    }

    private fun getNews() {
        viewModel.getNews()?.let { news ->
            binding.tvEmpty.hide()
            showNews(news)
        } ?: run {
            binding.tvEmpty.show()
            binding.tvEmpty.text = getString(R.string.search_favorite_topic)
        }
    }

    private fun searchNews(query: String) {
        viewModel.searchNews(query).observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.pbLoading.show()
                    binding.tvEmpty.hide()
                }
                is Resource.Success -> {
                    binding.pbLoading.hide()
                    resource.data?.let { news ->
                        if (news.isNotEmpty()) {
                            showNews(news)
                        } else {
                            showNoResults()
                        }
                    }
                }
                is Resource.Error -> {
                    binding.pbLoading.hide()
                    showError(resource.throwable.message)
                }
            }
        })
    }

    private fun showNews(news: ArrayList<News>) = with(binding) {
        adapter.clearAll()
        adapter.addAll(news)
    }

    private fun showError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showNoResults() {
        adapter.clearAll()
        binding.tvEmpty.show()
        binding.tvEmpty.text = getString(R.string.no_results_found)
    }
}