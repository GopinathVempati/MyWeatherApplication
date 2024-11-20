package com.weather.myweatherapplication.data.datasource

import com.weather.myweatherapplication.data.entity.CityEntity
import com.weather.myweatherapplication.data.entity.ForecastEntity
import com.weather.myweatherapplication.data.room.CityDao
import com.weather.myweatherapplication.data.room.ForecastDao
import javax.inject.Inject

class ForecastLocalDataSource @Inject constructor(
    private val forecastDao: ForecastDao,
    private val cityDao: CityDao
) {

    suspend fun addForecastWeather(forecastEntity: ForecastEntity) =
        forecastDao.addForecastWeather(forecastEntity)

    suspend fun addCity(cityEntity: CityEntity) =
        cityDao.addCity(cityEntity)

    fun getForecastWeather() = forecastDao.getForecastWeather()
    fun getCity() = cityDao.getCity()

    suspend fun updateForecastWeather(forecastEntity: ForecastEntity) =
        forecastDao.updateForecastWeather(forecastEntity)

    suspend fun updateCity(cityEntity: CityEntity) =
        cityDao.updateCity(cityEntity)
}