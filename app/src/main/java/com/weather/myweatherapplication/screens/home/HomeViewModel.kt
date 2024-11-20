package com.weather.myweatherapplication.screens.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weather.myweatherapplication.common.Resource
import com.weather.myweatherapplication.domain.model.City
import com.weather.myweatherapplication.domain.model.Forecast
import com.weather.myweatherapplication.domain.usecase.forecast.*
import com.weather.myweatherapplication.domain.usecase.location.GetLocationUseCase
import com.weather.myweatherapplication.network.ApiResult
import com.weather.myweatherapplication.utils.ExceptionTitles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addForecastDb: AddForecastToDbUseCase,
    private val addCityDb: AddCityToDbUseCase,
    private val updateCityDbUseCase: UpdateCityDbUseCase,
    private val getForecastDb: GetForecastFromDbUseCase,
    private val updateForecastDb: UpdateForecastDbUseCase,
    private val forcastWithCityName: GetForecastWithCityNameUseCase,
    private val getForecast: GetForecastUseCase, private val getCurrentLocation: GetLocationUseCase
) : ViewModel() {

    private val _result = MutableStateFlow<ApiResult>(ApiResult.Loading)
    val result = _result.asStateFlow()

    init {
        loadLocation()
    }

    fun loadLocation() {
        _result.value = ApiResult.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val locationData = getCurrentLocation.getLocation()
                if (locationData != null) {
                    fetchForecast(locationData.latitude, locationData.longitude)
                } else if (isForecastCached()) {
                    getCachedForecast()
                } else {
                    _result.value = ApiResult.Error(ExceptionTitles.NO_INTERNET_CONNECTION)
                }
            } catch (e: Exception) {
                if (isForecastCached()) {
                    getCachedForecast()
                } else {
                    _result.value = ApiResult.Error(e.message)
                }
            }
        }
    }

    private suspend fun fetchForecast(latitude: Double, longitude: Double) {
        when (val result = getForecast.getForecast(latitude, longitude)) {
            is Resource.Success -> {
                _result.value = ApiResult.Success(result.data)
                if (result.data != null) {
                    if (!isForecastCached()) {
                        cacheForecast(result.data, result.data.cityDtoData)
                    } else {
                        updateCachedForecast(result.data, result.data.cityDtoData)
                    }
                }
            }

            is Resource.Error -> {
                _result.value = ApiResult.Error(result.message)
            }
        }
    }

    private suspend fun fetchForcastWithCityName(city: String) {
        when (val result = forcastWithCityName.getForecast(city)) {
            is Resource.Success -> {
                _result.value = ApiResult.Success(result.data)
                if (result.data != null) {
                    if (!isForecastCached()) {
                        cacheForecast(result.data, result.data.cityDtoData)
                    } else {
                        updateCachedForecast(result.data, result.data.cityDtoData)
                    }
                }
            }

            is Resource.Error -> {
                _result.value = ApiResult.Error(result.message)
            }
        }
    }

    private suspend fun cacheForecast(forecast: Forecast, city: City) {
        addForecastDb.addForecastToDbUseCase(
            forecast,
            forecast.weatherList.size
        )
        addCityDb.addCityDb(city)
    }

    private suspend fun updateCachedForecast(forecast: Forecast, city: City) {
        updateForecastDb.updateForecastDbUseCase(
            forecast,
            forecast.weatherList.size
        )
        updateCityDbUseCase.updateCityDb(city)
    }

    // Data cannot be null.
    // Because before this function is called, it is checked for null with the isForecastCached() function.
    private fun getCachedForecast() {
        _result.value =
            ApiResult.Success(getForecastDb.getForecastFromDbUseCase())
    }

    private fun isForecastCached(): Boolean {
        return getForecastDb.getForecastFromDbUseCase() != null
    }

    fun performSearch(s: String) {
        viewModelScope.launch {
            fetchForcastWithCityName(s)
        }
    }
}
