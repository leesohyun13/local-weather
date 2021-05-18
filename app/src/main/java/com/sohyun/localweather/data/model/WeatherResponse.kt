package com.sohyun.localweather.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class WeatherResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("location_type")
    val locationType: String, // (City|Region / State / Province|Country|Continent)
    @SerializedName("latt_long")
    val lattLong: String, //  "37.271881,-119.270233"
    @SerializedName("time")
    val time: Date ,
    @SerializedName("sun_rise")
    val sunRise: Date,
    @SerializedName("sun_set")
    val sunSet: Date,
    @SerializedName("timezone_name")
    val timezoneName: String,
    @SerializedName("parent")
    val parent: Parent,
    @SerializedName("consolidated_weather")
    val consolidatedWeather: List<ConsolidatedWeather>,
    @SerializedName("sources")
    val sources: List<Sources>
) {
    data class Parent(
        @SerializedName("title")
        val title: String,
        @SerializedName("location_type")
        val locationType: String,
        @SerializedName("latt_long")
        val lattLong: String,
        @SerializedName("woeid")
        val woeid: Int
    )
    data class ConsolidatedWeather(
        @SerializedName("id")
        val id: Int,
        @SerializedName("applicable_date")
        val applicableDate: Date,
        @SerializedName("weather_state_name")
        val weatherStateName: String,
        @SerializedName("weather_state_abbr")
        val weatherStateAbbr: String,
        @SerializedName("wind_speed")
        val windSpeed: Float,
        @SerializedName("wind_direction")
        val windDirection: Float,
        @SerializedName("wind_direction_compass")
        val windDirectionCompass: String,
        @SerializedName("min_temp")
        val minTemp: Int,
        @SerializedName("max_temp")
        val maxTemp: Int,
        @SerializedName("the_temp")
        val theTemp: Int,
        @SerializedName("air_pressure")
        val airPressure: Float,
        @SerializedName("humidity")
        val humidity: Float,
        @SerializedName("humidity")
        val visibility: Float,
        @SerializedName("predictability")
        val predictability: Int
    )
    data class Sources(
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String
    )
}
