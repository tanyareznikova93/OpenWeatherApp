package com.weather.openweatherapp.model.weathrhourly


import com.google.gson.annotations.SerializedName

data class Hourly(
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val main: Main,
    val pop: Int,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)