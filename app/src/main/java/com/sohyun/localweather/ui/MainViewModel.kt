package com.sohyun.localweather.ui

import androidx.lifecycle.*
import com.sohyun.localweather.data.model.LocalWeather
import com.sohyun.localweather.data.model.LocationResponse
import com.sohyun.localweather.data.model.WeatherResponse
import com.sohyun.localweather.data.network.NetworkStatus
import com.sohyun.localweather.data.repository.WeatherRepository
import com.sohyun.localweather.getToday
import com.sohyun.localweather.getTomorrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    val scope = CoroutineScope(Job() + Dispatchers.IO) // fixme
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

    fun searchWeather() {
        isLoading.value = true
        val weatherItems = arrayListOf<LocalWeather>()
        viewModelScope.launch {
            locations.value?.let {
                weatherItems.add(LocalWeather("Local", 0, null, null)) // add title
                val todayDate = getToday()
                val tomorrowDate = getTomorrow()
                val todayDeferred: List<Deferred<NetworkStatus<List<WeatherResponse.ConsolidatedWeather>>>> = locations.value!!.map {
                    async {
                        weatherRepository.getWeather(it.woeid, todayDate)
                    }
                }
                val tomorrowDeferred: List<Deferred<NetworkStatus<List<WeatherResponse.ConsolidatedWeather>>>> = locations.value!!.map {
                    async {
                        weatherRepository.getWeather(it.woeid, tomorrowDate)
                    }
                }
                val todayResponse = todayDeferred.awaitAll()
                val tomorrowResponse = tomorrowDeferred.awaitAll()
                locations.value!!.forEachIndexed { index, location ->
                    val todayWeather = when(todayResponse[index]){
                        is NetworkStatus.Failure -> null
                        is NetworkStatus.Success -> (todayResponse[index] as NetworkStatus.Success).data
                    }
                    val tomorrowWeather = when(tomorrowResponse[index]){
                        is NetworkStatus.Failure -> null
                        is NetworkStatus.Success -> (tomorrowResponse[index] as NetworkStatus.Success).data
                    }
                    weatherItems.add(LocalWeather(location.title, location.woeid, todayWeather?.get(0), tomorrowWeather?.get(0)))
                }
                setLocalWeather(weatherItems)
            }
            isLoading.value = false
        }
    }


    private val locationsObserve = Observer<List<LocationResponse>> {
        it?.let { searchWeather() }
    }

    fun setLocalWeather(weatherItems: List<LocalWeather>) {
        localWeather.postValue(null)
        localWeather.postValue(weatherItems)
    }

    fun isLoading(): LiveData<Boolean> = isLoading
    fun getLocations(): LiveData<List<LocationResponse>> = locations
    fun getLocalWeather(): LiveData<List<LocalWeather>> = localWeather

    init {
        searchLocation()
        locations.observeForever(locationsObserve)
    }

    override fun onCleared() {
        scope.cancel()
        locations.removeObserver(locationsObserve)
        super.onCleared()
    }
}