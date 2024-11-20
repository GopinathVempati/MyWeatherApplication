package com.weather.myweatherapplication.data.remote.weatherapi

import com.weather.myweatherapplication.data.remote.entity.ForecastDto
import com.weather.myweatherapplication.utils.NetworkService
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(NetworkService.FORECAST_END_POINT)
    suspend fun getForecastData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("APPID") apiKey: String = NetworkService.API_KEY,
        @Query("units") units: String = NetworkService.UNITS,
    ): ForecastDto

    @GET(NetworkService.FORECAST_END_POINT)
    suspend fun getForecastDataWithCityName(
        @Query("q") cityName: String,
        @Query("APPID") apiKey: String = NetworkService.API_KEY,
        @Query("units") units: String = NetworkService.UNITS,
    ): ForecastDto
}