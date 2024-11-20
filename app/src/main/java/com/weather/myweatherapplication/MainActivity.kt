package com.weather.myweatherapplication

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.weather.myweatherapplication.nav.BottomNavigationBar
import com.weather.myweatherapplication.screens.home.HomeViewModel
import com.weather.myweatherapplication.ui.theme.MyWeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            homeViewModel.loadLocation()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        setContent {
            if (isConnected()) WeatherApp(homeViewModel)
            else RetryConnection(homeViewModel)
        }
    }
}

fun Context.isConnected(): Boolean {
    val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.isDefaultNetworkActive
    return networkInfo
}


@Composable
fun WeatherApp(homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val items = listOf("Home", "Favorites", "Settings")

    MyWeatherApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            BottomNavigationBar(navController, homeViewModel)
        }
    }

}

@Composable
fun RetryConnection(homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    MyWeatherApplicationTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Not connected to internet. Please try again.",
                fontSize = 12.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextButton(
                onClick = {
                    (context as MainActivity)
                        .setContent {
                            if (context.isConnected()) WeatherApp(homeViewModel)
                            else RetryConnection(homeViewModel)
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(containerColor = Color(0xFFd68118)),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Text(
                    "Try Again", fontWeight = FontWeight.Medium, color = Color.White
                )
            }
        }
    }
}