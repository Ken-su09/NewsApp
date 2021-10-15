package com.suonk.newsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.suonk.newsapp.databinding.FragmentNewsDetailsBinding
import com.suonk.newsapp.ui.activity.MainActivity
import com.suonk.newsapp.viewmodels.NewsAppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private var binding: FragmentNewsDetailsBinding? = null

    private val viewModel: NewsAppViewModel by activityViewModels()
    private lateinit var contextActivity: MainActivity

    private var link = ""

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    //region ============================================== UI ==============================================

    private fun initializeUI() {
        contextActivity = activity as MainActivity
        getNewsFromViewModel()
        initCustomToolbar()
    }

    private fun initCustomToolbar() {
        binding?.apply {
            backToNewsList.setOnClickListener {
                contextActivity.supportFragmentManager.popBackStack()
            }

            shareNews.setOnClickListener {
                val share = Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/training/sharing/")
                    putExtra(Intent.EXTRA_TITLE, "Introducing content previews")
                    data = link.toUri()
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }, null)
                startActivity(share)

            }
        }
    }

    private fun getNewsFromViewModel() {
        viewModel.article.observe(viewLifecycleOwner, { article ->
            if (article != null) {
                binding?.apply {
                    Glide.with(activity as MainActivity)
                        .load(article.urlToImage)
                        .centerCrop()
                        .into(newsImage)

                    newsTitle.text = article.title

                    if (article.content.length > 202) {
                        val parts = article.content.split("[")
                        newsContent.text = parts[0]
                    } else {
                        newsContent.text = article.content
                    }

                    if (article.author != null) {
                        newsAuthorValue.text = article.author
                    }

                    newsDate.text = getDateFromNewsDate(article.publishedAt)
                    newsLinkValue.text = article.url
                    link = article.url
                }
            }
        })
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

    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}