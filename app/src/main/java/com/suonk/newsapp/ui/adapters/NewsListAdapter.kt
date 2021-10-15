package com.suonk.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suonk.newsapp.databinding.ItemNewsBinding
import com.suonk.newsapp.models.data.Article
import com.suonk.newsapp.ui.activity.MainActivity
import com.suonk.newsapp.ui.fragments.main_pages.AllNewsFragment
import com.suonk.newsapp.ui.fragments.main_pages.BreakingNewsFragment

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
            binding.newsDateValue.text = getDateFromNewsDate(article.publishedAt)

            binding.newsImg.let { img ->
                Glide.with(activity)
                    .load(article.urlToImage)
                    .centerCrop()
                    .into(img)
            }

            binding.root.setOnClickListener {
                activity.showNewsDetails(article)
            }
        }

        private fun getDateFromNewsDate(date: String): String {
            val fullDate = date.split("T")
            val days = fullDate[0].split("-")
            val year = days[0]
            val month = when (days[0]) {
                "01" -> "Jan."
                "02" -> "Feb."
                "03" -> "Mar."
                "04" -> "Apr."
                "05" -> "May."
                "06" -> "Jun."
                "07" -> "Jul."
                "08" -> "Aug."
                "09" -> "Sep."
                "10" -> "Oct."
                "11" -> "Nov."
                "12" -> "Dec."
                else -> "Jan."
            }

            return days[2] + " " + month + " " + year
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