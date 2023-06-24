package com.thenewsapp.presentation.shownews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thenewsapp.data.model.News
import com.thenewsapp.databinding.ShowNewsItemBinding
import com.thenewsapp.presentation.loadUrl

class ShowNewsAdapter(
    private var news: ArrayList<News>,
    private val onNewsSelected: (News) -> Unit,
) : ListAdapter<News, ShowNewsAdapter.ShowNewsViewHolder>(DiffCallback()) {

    private lateinit var binding: ShowNewsItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowNewsViewHolder {
        binding = ShowNewsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowNewsViewHolder(binding)
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: ShowNewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    fun addAll(newsList: List<News>) {
        news.addAll(newsList)
        submitList(news)
    }

    fun clearAll() {
        news.clear()
    }

    inner class ShowNewsViewHolder(private val binding: ShowNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) = with(binding) {
            ivNews.loadUrl(news.urlToImage)
            tvTitle.text = news.title
            root.setOnClickListener {
                onNewsSelected(news)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<News>() {

        override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem == newItem

        override fun areContentsTheSame(oldItem: News, newItem: News) =
            oldItem.title == newItem.title
    }
}