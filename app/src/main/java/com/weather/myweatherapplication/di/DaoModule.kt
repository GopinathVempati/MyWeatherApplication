package com.weather.myweatherapplication.di

import com.weather.myweatherapplication.data.room.CityDao
import com.weather.myweatherapplication.data.room.ForecastDao
import com.weather.myweatherapplication.data.room.MyCityDao
import com.weather.myweatherapplication.data.room.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun bindCurrentWeatherDao(weatherDatabase: WeatherDatabase): CityDao =
        weatherDatabase.cityDao()

    @Provides
    @Singleton
    fun bindForecastDao(weatherDatabase: WeatherDatabase): ForecastDao =
        weatherDatabase.forecastWeatherDao()

    @Provides
    @Singleton
    fun bindMyCityDao(weatherDatabase: WeatherDatabase): MyCityDao =
        weatherDatabase.myCityDao()
}