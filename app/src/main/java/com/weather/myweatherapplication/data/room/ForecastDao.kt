package com.weather.myweatherapplication.data.room

import androidx.room.*
import com.weather.myweatherapplication.data.entity.ForecastEntity

@Dao
interface ForecastDao {
    @Insert
    suspend fun addForecastWeather(forecastEntity: ForecastEntity)

    @Query("SELECT * FROM forecast_data")
    fun getForecastWeather(): List<ForecastEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateForecastWeather(forecastEntity: ForecastEntity)
}