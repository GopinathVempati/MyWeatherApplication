package com.weather.myweatherapplication.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrentWeatherDetailRow(title1: String, value1: String, title2: String, value2: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CurrentWeatherDetailCard(title = title1, value = value1)

        CurrentWeatherDetailCard(title = title2, value = value2)
    }
}

@Composable
private fun CurrentWeatherDetailCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .size(130.dp)
            .background(MaterialTheme.colorScheme.secondary),
        shape = MaterialTheme.shapes.small,
        border = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, end = 15.dp), Alignment.Center
        ) {
            Text(
                text = title, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 14.sp)
            )
        }

        Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
            Text(
                text = value, style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
            )
        }
    }
}