package com.sohyun.localweather

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sohyun.localweather.data.network.WeatherApi.Companion.WEATHER_BASE_URL
import java.text.SimpleDateFormat
import java.util.*

fun getToday(): String {
    return SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
}

fun getTomorrow(): String {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, 1)
    return SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(cal.time)
}

fun setWeatherImage(states: String, view: ImageView) {
    Glide.with(view.context)
            .asBitmap()
            .load("${WEATHER_BASE_URL}static/img/weather/png/${states}.png")
            .into(view)
}