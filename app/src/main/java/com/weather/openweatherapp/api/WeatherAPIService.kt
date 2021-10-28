package com.weather.openweatherapp.api

import com.weather.openweatherapp.model.CurrentWeather
import com.weather.openweatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {

    //http://api.openweathermap.org/data/2.5/weather?q=london&APPID=461a6dc9004e4d04325d854e78571ab7

    //https://api.openweathermap.org/data/2.5/onecall?lat=55.7522&lon=37.6156&exclude=current&appid=461a6dc9004e4d04325d854e78571ab7&lang=ru

    private val BASE_URL = "http://api.openweathermap.org/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(cityName: String): Single<WeatherModel> {
        return api.getData(cityName)
    }

    fun getAllDataService(latCity: String, lonCity: String): Single<CurrentWeather> {
        return api.getAllData(latCity,lonCity)
    }


}