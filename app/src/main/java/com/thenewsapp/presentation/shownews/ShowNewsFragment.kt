package com.thenewsapp.presentation.shownews

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.thenewsapp.R
import com.thenewsapp.data.DependencyProvider
import com.thenewsapp.data.NewsService
import com.thenewsapp.data.db.Search
import com.thenewsapp.data.model.News
import com.thenewsapp.data.model.Result
import com.thenewsapp.databinding.ShowNewsFragmentBinding
import com.thenewsapp.presentation.NewsApplication
import com.thenewsapp.presentation.hide
import com.thenewsapp.presentation.show

class ShowNewsFragment : Fragment(), ShowNewsAdapter.NewsSelectedListener {

    private lateinit var binding: ShowNewsFragmentBinding

    private lateinit var actionListener: ActionListener

    private lateinit var adapter: ShowNewsAdapter

    private val newsService = DependencyProvider.provideService(NewsService::class.java)

    private val newsRepository = DependencyProvider.provideRepository(newsService)

    private val viewModel: SharedNewsViewModel by activityViewModels {
        SharedNewsViewModel.Factory(this, null, newsRepository,
            (requireActivity().application as NewsApplication).searchTermRepository)
    }

    interface ActionListener {
        fun showNewsDetailView(news: News, sharedImageView: ImageView)
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
        savedInstanceState: Bundle?,
    ): View {
        binding = ShowNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()
        setupAdapter()

        viewModel.getQuery()?.let { query ->
            // Get saved query from ViewModel state
            binding.svNews.setQuery(query, true)
            searchNewsAndObserve(query)
        } ?: run {
            // Show empty view
            binding.tvEmpty.show()
            binding.tvEmpty.text = getString(R.string.search_favorite_topic)
            searchNewsAndObserve("")
        }

        observeSearchTerms()
    }

    override fun onNewsSelected(news: News, sharedImageView: ImageView) {
        viewModel.setSelectedNews(news)
        actionListener.showNewsDetailView(news, sharedImageView)
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

        adapter = ShowNewsAdapter(arrayListOf(), this@ShowNewsFragment)
        rvNews.adapter = adapter
    }

    private fun searchNewsAndObserve(query: String) {
        viewModel.searchNews(query).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.pbLoading.show()
                    binding.tvEmpty.hide()
                }
                is Result.Success -> {
                    binding.pbLoading.hide()
                    val news = result.data
                    if (news.isNotEmpty()) {
                        showNews(news)

                        // Insert search query only if there are news
                        viewModel.insert(Search(query = query))
                    } else {
                        showNoResults()
                    }
                }
                is Result.Error -> {
                    binding.pbLoading.hide()
                    showError(result.exception.message)
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

    private fun observeSearchTerms() {
        viewModel.allSearchTerms.observe(viewLifecycleOwner, { searchTerms ->
            Log.d("Search terms: ", "$searchTerms")
        })
    }
}