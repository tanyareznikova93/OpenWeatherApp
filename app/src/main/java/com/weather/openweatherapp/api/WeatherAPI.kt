package com.weather.openweatherapp.api

import com.weather.openweatherapp.model.CurrentWeather
import com.weather.openweatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/weather?q=moscow&APPID=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //https://api.openweathermap.org/data/2.5/onecall?lat=55.7522&lon=37.6156&exclude=current&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru

    //Получаем данные для поиска погоды по стране
    @GET("data/2.5/weather?&units=metric&exclude=hourly,daily&&APPID=461a6dc9004e4d04325d854e78571ab7&lang=ru")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>

    //Получаем сразу все данные "lon":37.6156,"lat":55.7522
    @GET("data/2.5/onecall?lat=55.7522&lon=37.6156&exclude=current&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru")
    fun getAllData(
        @Query("lat") latCity: String,
        @Query("lon") lonCity: String
    ): Single<CurrentWeather>


}