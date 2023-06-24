package com.thenewsapp.presentation.shownews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thenewsapp.R
import com.thenewsapp.data.model.News
import com.thenewsapp.databinding.ShowNewsFragmentBinding
import com.thenewsapp.presentation.hide
import com.thenewsapp.presentation.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowNewsFragment : Fragment() {

    private lateinit var binding: ShowNewsFragmentBinding

    private lateinit var adapter: ShowNewsAdapter

    private val viewModel: SharedNewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ShowNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()
        setupAdapter()

        // Show empty view
        binding.tvEmpty.show()
        binding.tvEmpty.text = getString(R.string.search_favorite_topic)
        searchNewsAndObserve("")
    }

    private fun setupSearchView() {
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchNewsAndObserve(it)
                } ?: run {
                    Toast.makeText(
                        activity, getString(R.string.enter_search_criteria), Toast.LENGTH_SHORT
                    ).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupAdapter() = with(binding) {
        rvNews.layoutManager = LinearLayoutManager(activity)

        adapter = ShowNewsAdapter(arrayListOf(), ::onNewsSelected)
        rvNews.adapter = adapter
    }

    private fun searchNewsAndObserve(query: String) {
        viewModel.searchNews(query)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.newsState.collectLatest { result ->
                when (result) {
                    is NewsListUiState.Idle -> {
                        // no-op
                    }
                    is NewsListUiState.Loading -> {
                        binding.pbLoading.show()
                        binding.tvEmpty.hide()
                    }
                    is NewsListUiState.Success -> {
                        binding.pbLoading.hide()
                        binding.tvEmpty.hide()
                        val news = result.news
                        if (news.isNotEmpty()) {
                            showNews(news)
                        } else {
                            showNoResults()
                        }
                    }
                    is NewsListUiState.Error -> {
                        binding.pbLoading.hide()
                        binding.tvEmpty.hide()
                        showError(result.throwable.message)
                    }
                }
            }
        }

    }

    private fun showNews(news: List<News>) = with(binding) {
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

    private fun onNewsSelected(news: News) {
        viewModel.setSelectedNews(news)
        val action =
            ShowNewsFragmentDirections.actionShowNewsFragmentToNewsDetailFragment(news.title)
        findNavController().navigate(action)
    }
}