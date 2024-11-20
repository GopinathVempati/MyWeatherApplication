package com.weather.myweatherapplication.domain.usecase.forecast

import com.weather.myweatherapplication.data.repository.ForecastRepositoryImpl
import javax.inject.Inject

class GetForecastWithCityNameUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun getForecast(cityName: String) =
        forecastRepositoryImpl.getForecastDataWithCityName(cityName)
}