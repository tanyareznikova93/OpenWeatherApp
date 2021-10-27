package com.weather.openweatherapp.model


import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)