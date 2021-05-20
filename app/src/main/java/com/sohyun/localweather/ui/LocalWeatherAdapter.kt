package com.sohyun.localweather.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sohyun.localweather.data.model.LocalWeather
import com.sohyun.localweather.data.model.WeatherViewType
import com.sohyun.localweather.databinding.ItemLocalWeatherBinding
import com.sohyun.localweather.databinding.ItemLocalWeatherTitleBinding
import com.sohyun.localweather.setWeatherImage
import kotlin.math.floor

class LocalWeatherAdapter(
        private val context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<LocalWeather> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            WeatherViewType.TOP.type -> {
                val topViewHolder = ItemLocalWeatherTitleBinding.inflate(LayoutInflater.from(context), parent, false)
                TitleViewHolder(topViewHolder)
            }
            else -> {
                val weatherViewHolder = ItemLocalWeatherBinding.inflate(LayoutInflater.from(context), parent, false)
                ItemViewHolder(weatherViewHolder)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            WeatherViewType.TOP.type
        } else {
            WeatherViewType.WEATHER.type
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bind(items[position])
            }
            is TitleViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(val binding: ItemLocalWeatherBinding) :
            RecyclerView.ViewHolder(
                    binding.root
            ) {
        fun bind(item: LocalWeather) {
            with(binding) {
                location.text = item.location
                item.todayWeather?.let { todayWeather ->
                    todayWeatherStateName.text = todayWeather.weatherStateName
                    todayTemp.text = "${floor(todayWeather.theTemp).toInt()}C"// FIXME 부호
                    todayHumidity.text = "${floor(todayWeather.humidity).toInt()}%"
                    setWeatherImage(todayWeather.weatherStateAbbr, todayWeatherStateAbbr)
                }

                item.tomorrowWeather?.let { tomorrowWeather ->
                    tomorrowWeatherStateName.text = tomorrowWeather.weatherStateName
                    tomorrowTemp.text = "${floor(tomorrowWeather.theTemp).toInt()}C" // FIXME
                    tomorrowHumidity.text = "${floor(tomorrowWeather.humidity).toInt()}%"
                    setWeatherImage(tomorrowWeather.weatherStateAbbr, tomorrowWeatherStateAbbr)
                }
            }
        }
    }

    inner class TitleViewHolder(val binding: ItemLocalWeatherTitleBinding) :
            RecyclerView.ViewHolder(
                    binding.root
            ) {
        fun bind(item: LocalWeather) {
            // do nothing
            binding.location.text = item.location
        }
    }

    fun setItemAll(newItems: List<LocalWeather>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged() // FIXME
    }
}