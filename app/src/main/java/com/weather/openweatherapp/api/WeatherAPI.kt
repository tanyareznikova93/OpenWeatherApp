package com.weather.openweatherapp.api

import com.weather.openweatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/weather?q=london&APPID=461a6dc9004e4d04325d854e78571ab7

    @GET("data/2.5/weather?&units=metric&APPID=461a6dc9004e4d04325d854e78571ab7")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>

}