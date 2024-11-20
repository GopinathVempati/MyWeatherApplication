package com.weather.myweatherapplication.data.remote.entity

import com.google.gson.annotations.SerializedName

data class SysDto(
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
)