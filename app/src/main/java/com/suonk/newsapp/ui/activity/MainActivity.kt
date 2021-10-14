package com.suonk.newsapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suonk.newsapp.databinding.ActivityMainBinding
import com.suonk.newsapp.navigation.Coordinator
import com.suonk.newsapp.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator
    private lateinit var coordinator: Coordinator
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigator.activity = this
        coordinator = Coordinator(navigator)

        coordinator.showSplashScreen()

        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            coordinator.showMainScreen()
        }
    }
}