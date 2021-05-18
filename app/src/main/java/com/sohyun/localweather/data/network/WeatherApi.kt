package com.sohyun.localweather.data.network

import com.sohyun.localweather.data.model.LocationResponse
import com.sohyun.localweather.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET(SUB_SEARCH_LOCATION)
    suspend fun searchLocation(
        @Query(SCHEMA_QUERY) query: String,
    ): LocationResponse

    @GET(SUB_SEARCH_WEATHER)
    suspend fun searchWeather(
        @Path(SCHEMA_PATH) woeid: Int,
    ): WeatherResponse

    companion object {
        const val WEATHER_BASE_URL = "https://www.metaweather.com/"

        const val SUB_SEARCH_LOCATION = "api/location/search/"
        const val SUB_SEARCH_WEATHER = "/api/location/{woeid}/"

        const val SCHEMA_QUERY = "query"
        const val SCHEMA_PATH = "woeid"
    }
}