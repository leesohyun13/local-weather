package com.sohyun.localweather.data.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("latt_long")
    val lattLong: Float,
    @SerializedName("woeid")
    val woeid: Int,
    @SerializedName("distance")
    val distance: Int
)
