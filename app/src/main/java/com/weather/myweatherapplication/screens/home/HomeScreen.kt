package com.weather.myweatherapplication.screens.home

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.weather.myweatherapplication.R
import com.weather.myweatherapplication.component.ErrorCard
import com.weather.myweatherapplication.domain.model.Forecast
import com.weather.myweatherapplication.helpers.SetError
import com.weather.myweatherapplication.network.ApiResult
import com.weather.myweatherapplication.component.CircularProgressBar
import com.weather.myweatherapplication.component.CurrentWeatherDetailRow
import com.weather.myweatherapplication.utils.AppStrings
import com.weather.myweatherapplication.utils.ErrorCardConsts
import com.weather.myweatherapplication.utils.ExceptionTitles


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    val homeCurrentWeatherState by viewModel.result.collectAsState()
    val activity = (LocalContext.current as? Activity)

    BackgroundImage()
    WeatherSection(homeCurrentWeatherState, viewModel) { activity?.finish() }
}


@Composable
private fun BackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun WeatherSection(
    currentWeatherState: ApiResult,
    viewModel: HomeViewModel,
    errorCardOnClick: () -> Unit
) {
    when (currentWeatherState) {
        is ApiResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3))
            }
        }

        is ApiResult.Success -> {
            if (currentWeatherState.forecast != null) {
                UpdateUI(currentWeatherState.forecast, viewModel)
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


@Composable
private fun UpdateUI(todayWeather: Forecast, viewModel: HomeViewModel) {
    WhiteTextBasicTextFieldWithRoundedCornersAndSize(viewModel)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = todayWeather.cityDtoData.cityName,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
        )
        Text(
            text = "${todayWeather.weatherList[0].weatherData.temp.toInt()}${AppStrings.degree}",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
        )
        Text(
            text = todayWeather.weatherList[0].weatherStatus[0].description,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
        )
        Text(
            text = "H:${todayWeather.cityDtoData.coordinate.longitude}°  L:${todayWeather.cityDtoData.coordinate.latitude}°",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
        )
        WeatherDetailSection(todayWeather)
    }
}


@Composable
fun WhiteTextBasicTextFieldWithRoundedCornersAndSize(viewModel: HomeViewModel) {
    val c = LocalContext.current

    val text = remember { mutableStateOf("") }
    BasicTextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(Color.White, RoundedCornerShape(8.dp)),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                innerTextField()
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = Color.Black,
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.performSearch(text.value)
                        }
                )
            }
        }
    )
}

@Composable
private fun WeatherDetailSection(currentWeather: Forecast) {
    CurrentWeatherDetailRow(
        title1 = AppStrings.feels_like,
        value1 = "${currentWeather.weatherList[0].weatherData.feelsLike}${AppStrings.degree}",
        title2 = AppStrings.humidity,
        value2 = "${currentWeather.weatherList[0].weatherData.humidity}%"
    )
    CurrentWeatherDetailRow(
        title1 = AppStrings.wind,
        value1 = "${currentWeather.weatherList[0].wind.speed}${AppStrings.metric}",
        title2 = AppStrings.pressure,
        value2 = "${currentWeather.weatherList[0].weatherData.pressure}"
    )
}
