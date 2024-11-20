package com.weather.myweatherapplication.domain.usecase.forecast

import com.weather.myweatherapplication.common.Resource
import com.weather.myweatherapplication.data.repository.ForecastRepositoryImpl
import com.weather.myweatherapplication.domain.model.Forecast
import javax.inject.Inject


class GetForecastUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun getForecast(latitude: Double, longitude: Double): Resource<Forecast> =
        forecastRepositoryImpl.getForecastData(latitude, longitude)
}