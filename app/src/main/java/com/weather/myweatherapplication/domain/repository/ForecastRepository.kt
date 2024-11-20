package com.weather.myweatherapplication.domain.repository

import com.weather.myweatherapplication.common.Resource
import com.weather.myweatherapplication.domain.model.City
import com.weather.myweatherapplication.domain.model.Forecast

interface ForecastRepository {
    suspend fun getForecastData(latitude: Double, longitude: Double, ): Resource<Forecast>

    suspend fun getForecastDataWithCityName(cityName: String): Resource<Forecast>

    suspend fun addForecastWeather(forecast: Forecast)

    suspend fun addCity(city: City)

    fun getForecastWeather() : Forecast?

    fun getCity() : City

    suspend fun updateForecastWeather(forecast: Forecast)

    suspend fun updateCity(city: City)
}