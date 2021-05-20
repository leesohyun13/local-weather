package com.sohyun.localweather.ui

import androidx.lifecycle.*
import com.sohyun.localweather.data.model.LocalWeather
import com.sohyun.localweather.data.model.LocationResponse
import com.sohyun.localweather.data.network.NetworkStatus
import com.sohyun.localweather.data.repository.WeatherRepository
import com.sohyun.localweather.getToday
import com.sohyun.localweather.getTomorrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private val isLoading = MutableLiveData(false)
    private val locations = MutableLiveData<List<LocationResponse>>(null)
    private val localWeather = MutableLiveData<List<LocalWeather>>().apply {
        value = arrayListOf()
    }

    private fun searchLocation() {
        isLoading.value = true
        viewModelScope.launch {
            val response = weatherRepository.searchLocation("se")
            when(response) {
                is NetworkStatus.Failure -> {}
                is NetworkStatus.Success -> {
                    locations.postValue(response.data)
                }
            }
        }
    }

    private fun searchWeather() {
        val weatherItems = arrayListOf<LocalWeather>()
        viewModelScope.launch {
            locations.value?.let {
                weatherItems.add(LocalWeather("Local", 0, null, null))
                val todayDate = getToday() // FIXME
                val tomorrowDate = getTomorrow() // FIXME
                for(location in locations.value!!) {
                    val todayWeatherDeferred = async { weatherRepository.getWeather(location.woeid, todayDate) }
                    val tomorrowWeatherDeferred = async { weatherRepository.getWeather(location.woeid, tomorrowDate) }
                    val response = awaitAll(todayWeatherDeferred, tomorrowWeatherDeferred)
                    val todayWeather = when(response[0]){
                        is NetworkStatus.Failure -> null
                        is NetworkStatus.Success -> (response[0] as NetworkStatus.Success).data
                    }
                    val tomorrowWeather = when(response[1]){
                        is NetworkStatus.Failure -> null
                        is NetworkStatus.Success -> (response[1] as NetworkStatus.Success).data
                    }
                    weatherItems.add(LocalWeather(location.title, location.woeid, todayWeather?.get(0), tomorrowWeather?.get(0))) // FIXME
                }
                localWeather.postValue(weatherItems)
            }
            isLoading.value = false
        }
    }

    private val locationsObserve = Observer<List<LocationResponse>> {
        it?.let { searchWeather() }
    }

    fun isLoading(): LiveData<Boolean> = isLoading
    fun getLocations(): LiveData<List<LocationResponse>> = locations
    fun getLocalWeather(): LiveData<List<LocalWeather>> = localWeather

    init {
        searchLocation()
        locations.observeForever(locationsObserve)
    }

    override fun onCleared() {
        locations.removeObserver(locationsObserve)
        super.onCleared()
    }
}