package com.weather.myweatherapplication.domain.usecase.forecast

import com.weather.myweatherapplication.data.repository.ForecastRepositoryImpl
import com.weather.myweatherapplication.domain.model.Forecast
import com.weather.myweatherapplication.domain.model.ForecastWeather
import javax.inject.Inject

class AddForecastToDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun addForecastToDbUseCase(forecast: Forecast, forecastSize: Int) {
        for (i in 1..forecastSize) {
            forecastRepositoryImpl.addForecastWeather(
                Forecast(
                    listOf(
                        ForecastWeather(
                            i,
                            forecast.weatherList[i - 1].weatherData,
                            forecast.weatherList[i - 1].weatherStatus,
                            forecast.weatherList[i - 1].wind,
                            forecast.weatherList[i - 1].date,
                            forecast.weatherList[i - 1].cloudiness
                        )
                    ),
                    forecast.cityDtoData
                )
            )
        }
    }
}