package com.sohyun.localweather.di

import com.sohyun.localweather.data.repository.WeatherRepository
import com.sohyun.localweather.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindWeatherRepository(
        WeatherRepository: WeatherRepositoryImpl
    ): WeatherRepository
}