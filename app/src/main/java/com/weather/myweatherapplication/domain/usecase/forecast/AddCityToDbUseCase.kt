package com.weather.myweatherapplication.domain.usecase.forecast

import com.weather.myweatherapplication.data.repository.ForecastRepositoryImpl
import com.weather.myweatherapplication.domain.model.City
import javax.inject.Inject

class AddCityToDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun addCityDb(city: City) = forecastRepositoryImpl.addCity(city)
}