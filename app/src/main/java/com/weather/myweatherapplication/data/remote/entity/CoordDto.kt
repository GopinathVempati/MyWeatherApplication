package com.weather.myweatherapplication.data.remote.entity

import com.google.gson.annotations.SerializedName

data class CoordDto(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
)
