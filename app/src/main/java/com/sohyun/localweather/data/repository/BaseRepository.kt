package com.sohyun.localweather.data.repository

import com.sohyun.localweather.data.network.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkStatus<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkStatus.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkStatus.Failure(isNetworkError = false, errorCode = throwable.code(), errorBody = throwable.response()?.errorBody())
                    }
                    else -> {
                        NetworkStatus.Failure(true, null, null)
                    }
                }
            }
        }
    }
}