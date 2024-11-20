package com.weather.myweatherapplication.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cabin
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route : String) {
    object Splash : Screens("splash_screen")
    object Home : Screens("home_screen")
    object Profile : Screens("profile_screen")
}

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "Forecast",
                icon = Icons.Filled.Cabin,
                route = Screens.Profile.route
            ),
        )
    }
}