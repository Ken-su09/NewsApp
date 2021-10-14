package com.suonk.newsapp.navigation

class Coordinator(private val navigator: Navigator) {

    fun showSplashScreen() {
        navigator.showSplashScreen()
    }

    fun showMainScreen() {
        navigator.showMainScreen()
    }
}