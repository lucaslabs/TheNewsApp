package com.thenewsapp.presentation.shownews

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.thenewsapp.data.model.News
import com.thenewsapp.databinding.ShowNewsItemBinding
import com.thenewsapp.presentation.loadUrl

class ShowNewsAdapter(
    private var news: ArrayList<News>,
    private val listener: NewsSelectedListener
) :
    RecyclerView.Adapter<ShowNewsAdapter.ShowNewsViewHolder>() {

    private lateinit var binding: ShowNewsItemBinding

    interface NewsSelectedListener {
        fun onNewsSelected(news: News, sharedImageView: ImageView)
    }

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
        notifyDataSetChanged()
    }

    fun clearAll() {
        news.clear()
        notifyDataSetChanged()
    }

    inner class ShowNewsViewHolder(private val binding: ShowNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) = with(binding) {
            ivNews.loadUrl(news.urlToImage)
            tvTitle.text = news.title
            tvSource.text = news.source.name
            root.setOnClickListener {
                listener.onNewsSelected(news, ivNews)
            }
        }
    }
}