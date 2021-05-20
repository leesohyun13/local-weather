package com.sohyun.localweather.data.repository

import com.sohyun.localweather.data.model.LocationResponse
import com.sohyun.localweather.data.model.WeatherResponse
import com.sohyun.localweather.data.network.NetworkStatus

interface WeatherRepository {
    suspend fun searchLocation(query: String): NetworkStatus<List<LocationResponse>>
    suspend fun getWeather(woeid: Int, date: String): NetworkStatus<List<WeatherResponse.ConsolidatedWeather>>
}