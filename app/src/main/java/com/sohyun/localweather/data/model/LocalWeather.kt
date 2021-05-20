package com.sohyun.localweather.data.model

data class LocalWeather(
        val location: String,
        val woeid: Int,
        val todayWeather: WeatherResponse.ConsolidatedWeather?,
        val tomorrowWeather: WeatherResponse.ConsolidatedWeather?
)