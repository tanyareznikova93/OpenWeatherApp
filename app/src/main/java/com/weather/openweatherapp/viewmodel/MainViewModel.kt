package com.weather.openweatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.openweatherapp.model.weather.WeatherModel
import com.weather.openweatherapp.api.WeatherAPIService
import com.weather.openweatherapp.model.forecast.ForecastModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private val weatherApiService = WeatherAPIService()
    private val disposable = CompositeDisposable()

    //current weather
    val weather_data = MutableLiveData<WeatherModel>()
    val weather_error = MutableLiveData<Boolean>()
    val weather_loading = MutableLiveData<Boolean>()

    //get list of city names
    val weather_data_fav_city = MutableLiveData<WeatherModel>()
    val weather_error_fav_city = MutableLiveData<Boolean>()
    val weather_loading_fav_city = MutableLiveData<Boolean>()

    //hourly forecast weather
    val forecast_weather_data = MutableLiveData<ForecastModel>()
    val forecast_weather_error = MutableLiveData<Boolean>()
    val forecast_weather_loading = MutableLiveData<Boolean>()

    //daily forecast weather
    val forecast_weather_data2 = MutableLiveData<ForecastModel>()
    val forecast_weather_error2 = MutableLiveData<Boolean>()
    val forecast_weather_loading2 = MutableLiveData<Boolean>()

    //refresh current weather
    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }

    //refresh list if city names
    fun refreshDataForFavCity(cityName: String) {
        getDataForFavCityFromAPI(cityName)
    }

    //refresh hourly weather
    fun refreshForecastData(cityName: String) {
        getDataForecastFromAPI(cityName)
    }

    //refresh daily weather
    fun refreshForecastData2(cityName: String) {
        getDataForecastFromAPI2(cityName)
    }

    //get data from API (current weather)
    private fun getDataFromAPI(cityName: String) {

        weather_loading.value = true
        disposable.add(
            weatherApiService.getDataService(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {

                    override fun onSuccess(t: WeatherModel) {
                        weather_data.value = t
                        weather_error.value = false
                        weather_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weather_error.value = true
                        weather_loading.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }//getDataFromAPI

    //get data from API (list of city names)
    private fun getDataForFavCityFromAPI(cityName: String) {

        weather_loading_fav_city.value = true
        disposable.add(
            weatherApiService.getDataServiceForFavCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {

                    override fun onSuccess(t: WeatherModel) {
                        weather_data_fav_city.value = t
                        weather_error_fav_city.value = false
                        weather_loading_fav_city.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weather_error_fav_city.value = true
                        weather_loading_fav_city.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }//getDataForFavCityFromAPI

    //get data from API (hourly weather)
    private fun getDataForecastFromAPI(cityName: String) {

        forecast_weather_loading.value = true
        disposable.add(
            weatherApiService.getDataServiceFromForecast(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ForecastModel>() {

                    override fun onSuccess(t: ForecastModel) {
                        forecast_weather_data.value = t
                        forecast_weather_error.value = false
                        forecast_weather_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        forecast_weather_error.value = true
                        forecast_weather_loading.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }//getDataForecastFromAPI

    //get data from API (daily weather)
    private fun getDataForecastFromAPI2(cityName: String) {

        forecast_weather_loading2.value = true
        disposable.add(
            weatherApiService.getDataServiceFromForecast2(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ForecastModel>() {

                    override fun onSuccess(t: ForecastModel) {
                        forecast_weather_data2.value = t
                        forecast_weather_error2.value = false
                        forecast_weather_loading2.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        forecast_weather_error2.value = true
                        forecast_weather_loading2.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }//getDataForecastFromAPI2

}//MainViewModel