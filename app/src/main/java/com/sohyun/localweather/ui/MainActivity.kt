package com.sohyun.localweather.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohyun.localweather.R
import com.sohyun.localweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var localWeatherAdapter: LocalWeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = mainViewModel
        binding.lifecycleOwner = this@MainActivity

        binding.weatherRecycerview.run {
            localWeatherAdapter = LocalWeatherAdapter(this@MainActivity)
            adapter = localWeatherAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mainViewModel.getLocalWeather().observe(this, { weathers ->
            weathers?.let { localWeatherAdapter.setItemAll(weathers) }
        })
    }
}