package com.weather.myweatherapplication.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.myweatherapplication.domain.model.ForecastWeather
import com.weather.myweatherapplication.helpers.HourConverter
import com.weather.myweatherapplication.utils.WeatherType

@Composable
fun ForecastLazyRow(forecasts: List<ForecastWeather>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecasts) {
            if (forecasts.size == 8) {
                HourlyCard(
                    time = HourConverter.convertHour(it.date.substring(11, 13)),
                    image = WeatherType.setWeatherType(
                        it.weatherStatus[0].mainDescription,
                        it.weatherStatus[0].description,
                        HourConverter.convertHour(it.date.substring(11, 13)),
                    ),
                    temperature = "${it.weatherData.temp.toInt()}°"
                )
            } else {
                HourlyCard(
                    time = HourConverter.convertHour(it.date.substring(11, 13)),
                    image = WeatherType.setWeatherType(
                        it.weatherStatus[0].mainDescription,
                        it.weatherStatus[0].description,
                        HourConverter.convertHour(it.date.substring(11, 13)),
                    ),
                    temperature = "${it.weatherData.temp.toInt()}°"
                )
            }
        }
    }
}

@Composable
fun HourlyCard(image: Int, time: String, temperature: String) {
    var color = MaterialTheme.colorScheme.onPrimary
    var tapped by remember {
        mutableStateOf(false)
    }
    if (tapped) {
        color = MaterialTheme.colorScheme.onSecondary
    }
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(80.dp)
            .padding(start = 15.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = image), contentDescription = null, modifier = Modifier.size(30.dp), contentScale = ContentScale.Crop)

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = time, fontSize = 16.sp, color = Color.Black)
                Text(
                    text = "$temperature C",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

        }
    }
}
