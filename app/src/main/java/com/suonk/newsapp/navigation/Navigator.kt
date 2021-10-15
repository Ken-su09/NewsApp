package com.suonk.newsapp.navigation

import androidx.fragment.app.FragmentActivity
import com.suonk.newsapp.R
import com.suonk.newsapp.ui.fragments.NewsDetailsFragment
import com.suonk.newsapp.ui.fragments.main_pages.MainFragment
import com.suonk.newsapp.ui.fragments.SplashScreenFragment
import javax.inject.Inject

class Navigator @Inject constructor(var activity: FragmentActivity?) {

    fun showSplashScreen() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, SplashScreenFragment())
            .commit()
    }

    fun showMainScreen() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, MainFragment())
            .commit()
    }

    fun showNewsDetails() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, NewsDetailsFragment())
            .addToBackStack(null)
            .commit()
    }
}