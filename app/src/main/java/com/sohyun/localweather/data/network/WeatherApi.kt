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
    ): List<LocationResponse>

    @GET(SUB_SEARCH_WEATHER)
    suspend fun searchWeather(
        @Path(SCHEMA_PATH_WOEID) woeid: Int,
        @Path(SCHEMA_PATH_DATE) date: String,
    ): List<WeatherResponse.ConsolidatedWeather>

    companion object {
        const val WEATHER_BASE_URL = "https://www.metaweather.com/"

        const val SUB_SEARCH_LOCATION = "api/location/search/"
        const val SUB_SEARCH_WEATHER = "api/location/{woeid}/{date}/"

        const val SCHEMA_QUERY = "query"
        const val SCHEMA_PATH_WOEID = "woeid"
        const val SCHEMA_PATH_DATE = "date"
    }
}