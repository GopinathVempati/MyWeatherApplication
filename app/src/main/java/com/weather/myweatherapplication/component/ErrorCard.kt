package com.weather.myweatherapplication.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weather.myweatherapplication.R

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    errorTitle: String,
    errorDescription: String,
    errorButtonText: String,
    onClick: () -> Unit,
    cardModifier: Modifier,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Card(modifier = cardModifier) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.error),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            modifier = Modifier.size(64.dp),
                            painter = painterResource(id = R.drawable.error),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Text(text = errorTitle, style = MaterialTheme.typography.headlineSmall)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center ,
                            text = errorDescription,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Button(
                            onClick = onClick,
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = errorButtonText.uppercase(),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}