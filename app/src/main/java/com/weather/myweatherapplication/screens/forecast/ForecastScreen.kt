package com.weather.myweatherapplication.screens.forecast

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.weather.myweatherapplication.component.CircularProgressBar
import com.weather.myweatherapplication.component.ErrorCard
import com.weather.myweatherapplication.component.ForecastLazyCol
import com.weather.myweatherapplication.component.ForecastLazyRow
import com.weather.myweatherapplication.component.ForecastTitle
import com.weather.myweatherapplication.domain.model.Forecast
import com.weather.myweatherapplication.helpers.SetError
import com.weather.myweatherapplication.network.ApiResult
import com.weather.myweatherapplication.screens.home.HomeViewModel
import com.weather.myweatherapplication.utils.AppStrings
import com.weather.myweatherapplication.utils.ErrorCardConsts
import com.weather.myweatherapplication.utils.ExceptionTitles


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastScreen(
    homeViewModel: HomeViewModel
) {
    val homeCurrentWeatherState by homeViewModel.result.collectAsState()
    WeatherSection(homeCurrentWeatherState) { }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherSection(currentWeatherState: ApiResult, errorCardOnClick: () -> Unit) {
    when (currentWeatherState) {
        is ApiResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3))
            }
        }

        is ApiResult.Success -> {
            if (currentWeatherState.forecast != null) {
                ForecastSection(currentWeatherState.forecast)
            }
        }

        is ApiResult.Error -> {
            ErrorCard(
                modifier = Modifier.fillMaxSize(),
                errorTitle = currentWeatherState.errorMessage ?: ExceptionTitles.UNKNOWN_ERROR,
                errorDescription = SetError.setErrorCard(
                    currentWeatherState.errorMessage ?: ExceptionTitles.UNKNOWN_ERROR
                ),
                errorButtonText = ErrorCardConsts.BUTTON_TEXT,
                errorCardOnClick,
                cardModifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp / 4 + 48.dp)
                    .padding(horizontal = 64.dp)
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ForecastSection(forecastData: Forecast) {
    Column {
    ForecastTitle(text = AppStrings.hourly_forecast)
    ForecastLazyRow(forecasts = forecastData.weatherList.take(8))
    ForecastTitle(text = AppStrings.daily_forecast)
    ForecastLazyCol(forecasts = forecastData.weatherList.takeLast(32))
    }
}