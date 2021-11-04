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

    val weather_data = MutableLiveData<WeatherModel>()
    val weather_error = MutableLiveData<Boolean>()
    val weather_loading = MutableLiveData<Boolean>()

    val weather_data_fav_city = MutableLiveData<WeatherModel>()
    val weather_error_fav_city = MutableLiveData<Boolean>()
    val weather_loading_fav_city = MutableLiveData<Boolean>()
    //val all_weather_data = MutableLiveData<CurrentWeather>()

    val forecast_weather_data = MutableLiveData<ForecastModel>()
    val forecast_weather_error = MutableLiveData<Boolean>()
    val forecast_weather_loading = MutableLiveData<Boolean>()

    val forecast_weather_data2 = MutableLiveData<ForecastModel>()
    val forecast_weather_error2 = MutableLiveData<Boolean>()
    val forecast_weather_loading2 = MutableLiveData<Boolean>()


    //val all_w_data = MutableLiveData<AllWeatherModel>()
    //val all_w_loading = MutableLiveData<Boolean>()
    //val all_w_error = MutableLiveData<Boolean>()



    //val current_data = MutableLiveData<CurrentWeatherModel>()
    //val current_loading = MutableLiveData<Boolean>()
    //val current_error = MutableLiveData<Boolean>()

    //val hourly_data = MutableLiveData<HourlyWeatherModel>()
    //val hourly_loading = MutableLiveData<Boolean>()
    //val hourly_error = MutableLiveData<Boolean>()

    //val daily_data = MutableLiveData<DailyWeatherModel>()
    //val daily_loading = MutableLiveData<Boolean>()
    //val daily_error = MutableLiveData<Boolean>()



    //val hourly_weather_data = MutableLiveData<Hourly>()
    //val daily_weather_data = MutableLiveData<Daily>()

    //val weather_error2 = MutableLiveData<Boolean>()
    //val weather_loading2 = MutableLiveData<Boolean>()

    //val weather_error3 = MutableLiveData<Boolean>()
    //val weather_loading3 = MutableLiveData<Boolean>()

    //val daily_weather_data = MutableLiveData<DailyWeather>()

    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }

    fun refreshDataForFavCity(cityName: String) {
        getDataForFavCityFromAPI(cityName)
    }

    fun refreshForecastData(cityName: String) {
        getDataForecastFromAPI(cityName)
    }


    fun refreshForecastData2(cityName: String) {
        getDataForecastFromAPI2(cityName)
    }



    /*
    fun refreshHourlyForecastData(cityName: String) {
        getHourlyDataForecastFromAPI(cityName)
    }

    fun refreshDailyForecastData(cityName: String) {
        getDailyDataForecastFromAPI(cityName)
    }

     */


    /*

    fun refreshAllData(latCity: String, lonCity: String) {
        getAllDataFromAPI(latCity,lonCity)
    }

    fun refreshAllDataHourly(latCity: String, lonCity: String) {
        getAllDataFromAPIHourly(latCity,lonCity)
    }

    fun refreshAllDataDaily(latCity: String, lonCity: String) {
        getAllDataFromAPIDaily(latCity,lonCity)
    }

    fun refreshAllWeatherData(latCity: String, lonCity: String) {
        getAllWeatherDataFromAPI(latCity,lonCity)
    }

     */


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



    /*

    private fun getHourlyDataForecastFromAPI(cityName: String) {

        forecast_weather_loading.value = true
        disposable.add(
            weatherApiService.getHourlyDataServiceFromForecast(cityName)
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

    }//getHourlyDataForecastFromAPI

    private fun getDailyDataForecastFromAPI(cityName: String) {

        forecast_weather_loading.value = true
        disposable.add(
            weatherApiService.getDailyDataServiceFromForecast(cityName)
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

    }//getDailyDataForecastFromAPI

     */



/*
    private fun getAllWeatherDataFromAPI(latCity: String, lonCity: String) {

        all_w_loading.value = true
        disposable.add(
            weatherApiService.getAllWeatherDataService(latCity, lonCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<AllWeatherModel>() {

                    override fun onSuccess(t: AllWeatherModel) {
                        all_w_data.value = t
                        all_w_error.value = false
                        all_w_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        all_w_error.value = true
                        all_w_loading.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }//getAllWeatherDataFromAPI


    private fun getAllDataFromAPI(latCity: String, lonCity: String) {

        weather_loading2.value = true
        disposable.add(
            weatherApiService.getAllDataService(latCity, lonCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CurrentWeather>() {

                    override fun onSuccess(t: CurrentWeather) {
                        all_weather_data.value = t
                        weather_error2.value = false
                        weather_loading2.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weather_error2.value = true
                        weather_loading2.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }//getAllDataFromAPI

    private fun getAllDataFromAPIHourly(latCity: String, lonCity: String) {

        weather_loading2.value = true
        disposable.add(
            weatherApiService.getAllDataServiceHourly(latCity, lonCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Hourly>() {

                    override fun onSuccess(t: Hourly) {
                        hourly_weather_data.value = t
                        weather_error2.value = false
                        weather_loading2.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weather_error2.value = true
                        weather_loading2.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }//getAllDataFromAPIHourly

    private fun getAllDataFromAPIDaily(latCity: String, lonCity: String) {

        weather_loading3.value = true
        disposable.add(
            weatherApiService.getAllDataServiceDaily(latCity, lonCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Daily>() {

                    override fun onSuccess(t: Daily) {
                        daily_weather_data.value = t
                        weather_error3.value = false
                        weather_loading3.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weather_error3.value = true
                        weather_loading3.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }//getAllDataFromAPIDaily

 */

}