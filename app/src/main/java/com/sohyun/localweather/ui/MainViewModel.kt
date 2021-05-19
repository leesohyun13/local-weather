package com.sohyun.localweather.ui

import androidx.lifecycle.ViewModel
import com.sohyun.localweather.data.network.NetworkStatus
import com.sohyun.localweather.data.repository.WeatherRepository
import com.sohyun.localweather.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    suspend fun searchLocation() {
        val response = weatherRepository.searchLocation("se")
        when(response) {
            is NetworkStatus.Failure -> {}
            is NetworkStatus.Success -> {
                response.data
            }
        }
    }
}