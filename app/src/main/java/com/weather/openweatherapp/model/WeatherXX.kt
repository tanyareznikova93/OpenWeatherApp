package com.weather.openweatherapp.model


import com.google.gson.annotations.SerializedName

data class WeatherXX(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)