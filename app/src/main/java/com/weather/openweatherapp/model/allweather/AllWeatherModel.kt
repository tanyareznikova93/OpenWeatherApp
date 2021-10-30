package com.weather.openweatherapp.model.allweather


import com.google.gson.annotations.SerializedName

data class AllWeatherModel(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)