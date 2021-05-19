package com.sohyun.localweather.data.repository

import com.sohyun.localweather.data.model.LocationResponse
import com.sohyun.localweather.data.model.WeatherResponse
import com.sohyun.localweather.data.network.NetworkStatus
import com.sohyun.localweather.data.network.WeatherApi
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
        private val weatherApi: WeatherApi
) : WeatherRepository, BaseRepository() {
    override suspend fun searchLocation(query: String): NetworkStatus<List<LocationResponse>> =
            safeApiCall { weatherApi.searchLocation(query = query) }

    override suspend fun getWeather(woeid: Int, date: String): NetworkStatus<WeatherResponse> =
            safeApiCall { weatherApi.searchWeather(woeid = woeid, date = date) }
}