package com.sohyun.localweather.data.network

import okhttp3.ResponseBody

sealed class NetworkStatus<out T> {
    data class Success<out T>(val data: T) : NetworkStatus<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : NetworkStatus<Nothing>()
}