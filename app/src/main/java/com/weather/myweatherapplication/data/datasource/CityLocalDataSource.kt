package com.weather.myweatherapplication.data.datasource

import com.weather.myweatherapplication.data.entity.CityEntity
import com.weather.myweatherapplication.data.room.CityDao
import javax.inject.Inject

class CityLocalDataSource @Inject constructor(private val cityDao: CityDao) {

    suspend fun addCity(cityEntity: CityEntity) =
        cityDao.addCity(cityEntity)

    fun getCity() = cityDao.getCity()
}