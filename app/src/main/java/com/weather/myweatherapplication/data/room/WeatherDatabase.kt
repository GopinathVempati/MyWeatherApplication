package com.weather.myweatherapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.myweatherapplication.data.entity.CityEntity
import com.weather.myweatherapplication.data.entity.ForecastEntity
import com.weather.myweatherapplication.data.entity.MyCityEntity
import com.weather.myweatherapplication.data.room.CityDao
import com.weather.myweatherapplication.data.room.ForecastDao
import com.weather.myweatherapplication.data.room.MyCityDao

@Database(entities = [CityEntity::class, ForecastEntity::class, MyCityEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun forecastWeatherDao(): ForecastDao

    abstract fun myCityDao(): MyCityDao
}