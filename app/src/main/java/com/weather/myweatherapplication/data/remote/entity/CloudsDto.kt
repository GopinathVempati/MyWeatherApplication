package com.weather.myweatherapplication.data.remote.entity

import com.google.gson.annotations.SerializedName

data class CloudsDto(
    @SerializedName("all") val cloudiness: Int
)
