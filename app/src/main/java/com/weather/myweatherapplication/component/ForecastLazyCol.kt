package com.weather.myweatherapplication.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.myweatherapplication.domain.model.ForecastWeather
import com.weather.myweatherapplication.helpers.EpochConverter
import com.weather.myweatherapplication.helpers.HourConverter
import com.weather.myweatherapplication.utils.WeatherType
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastLazyCol(forecasts: List<ForecastWeather>) {
    LazyColumn (
        modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp)
    ) {
        items(forecasts) {

            val unixTime = EpochConverter.convertDateStringToLong(it.date)
            val instant = Instant.ofEpochSecond(unixTime)
            val zonedDateTime = instant.atZone(ZoneId.of("UTC"))
            val dayOfWeek = zonedDateTime.dayOfWeek.name.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            val month = zonedDateTime.month.name.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            val date = zonedDateTime.dayOfMonth
            val monthDate = "$month, $date"

            if (forecasts.size == 8) {
                DailyCard(
                    day = dayOfWeek,
                    date = monthDate,
                    image = WeatherType.setWeatherType(
                        it.weatherStatus[0].mainDescription,
                        it.weatherStatus[0].description,
                        HourConverter.convertHour(it.date.substring(11, 13)),
                    ),
                    temperature = "${it.weatherData.temp.toInt()}°"
                )
            } else {
                DailyCard(
                    day = dayOfWeek,
                    date = monthDate,
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
private fun WeatherCard(date: String? = null, time: String, weatherIcon: Int, degree: String) {
    Card(
        modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                if (date != null) {
                    Text(text = date, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
                }
                Text(text = time, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
            }
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = weatherIcon),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(text = degree, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp))
        }
    }
}

@Composable
fun DailyCard(day: String, date: String, temperature: String, image: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = 15.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.cardElevation(500.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = day,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = date,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$temperature°C",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
            Column(
                modifier = Modifier.padding(end = 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = image), contentDescription = null)

            }
        }
    }

}