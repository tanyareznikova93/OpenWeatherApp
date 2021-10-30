package com.weather.openweatherapp.model.daily


import com.google.gson.annotations.SerializedName

data class DailyWeatherModel(
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)