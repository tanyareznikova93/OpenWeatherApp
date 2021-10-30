package com.weather.openweatherapp.model.weathrhourly


import com.google.gson.annotations.SerializedName

data class WeatherHourlyModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Hourly>,
    val message: Int
)