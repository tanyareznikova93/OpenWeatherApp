package com.weather.openweatherapp.model.current


import com.google.gson.annotations.SerializedName

data class CurrentWeatherModel(
    val current: Current,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)