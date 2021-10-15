package com.suonk.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suonk.newsapp.databinding.ItemNewsBinding
import com.suonk.newsapp.models.data.Article
import com.suonk.newsapp.ui.activity.MainActivity

class NewsListAdapter(private val activity: MainActivity) :
    ListAdapter<Article, NewsListAdapter.ViewHolder>(NewsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.onBind(article)
    }

    inner class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(article: Article) {
            binding.newsTitle.text = article.title
            binding.newsDateValue.text = article.publishedAt

            binding.newsImg.let { img ->
                Glide.with(activity)
                    .load(article.urlToImage)
                    .centerCrop()
                    .into(img)
            }
        }
    }

    class NewsComparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.author == newItem.author &&
                    oldItem.content == newItem.content &&
                    oldItem.description == newItem.description &&
                    oldItem.publishedAt == newItem.publishedAt &&
                    oldItem.source == newItem.source &&
                    oldItem.url == newItem.url &&
                    oldItem.urlToImage == newItem.urlToImage
        }
    }
}