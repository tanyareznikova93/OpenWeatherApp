package com.weather.openweatherapp.model.current


import com.google.gson.annotations.SerializedName

data class Minutely(
    val dt: Int,
    val precipitation: Int
)