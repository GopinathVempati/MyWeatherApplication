package com.weather.myweatherapplication.network

import com.weather.myweatherapplication.domain.model.Forecast

sealed interface ApiResult {
    data class Success(val forecast: Forecast?): ApiResult
    data class Error(val errorMessage: String?): ApiResult

    object Loading: ApiResult
}