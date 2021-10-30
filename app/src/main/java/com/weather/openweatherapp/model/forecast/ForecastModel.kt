package com.weather.openweatherapp.model.forecast


import com.google.gson.annotations.SerializedName

data class ForecastModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Forecast>,
    val message: Int
)