package com.weather.myweatherapplication.domain.usecase.forecast

import com.weather.myweatherapplication.data.repository.ForecastRepositoryImpl
import com.weather.myweatherapplication.domain.model.City
import javax.inject.Inject

class UpdateCityDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun updateCityDb(city: City) = forecastRepositoryImpl.updateCity(city)
}