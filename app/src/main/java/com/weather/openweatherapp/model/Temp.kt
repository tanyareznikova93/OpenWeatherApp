package com.weather.openweatherapp.model


import com.google.gson.annotations.SerializedName

data class Temp(
    val day: Double,
    val eve: Int,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)