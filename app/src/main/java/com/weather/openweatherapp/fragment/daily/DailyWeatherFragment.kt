package com.weather.openweatherapp.fragment.daily

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.weather.openweatherapp.R
//import com.weather.openweatherapp.fragment.current.CurrentWeatherFragment
import com.weather.openweatherapp.model.alldata.CurrentWeather
import com.weather.openweatherapp.utils.APP_ACTIVITY
//import com.weather.openweatherapp.utils.replaceActivity
import com.weather.openweatherapp.utils.restartActivity
import com.weather.openweatherapp.viewmodel.MainViewModel
//import com.weather.openweatherapp.viewmodel.TAG
//import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.android.synthetic.main.fragment_daily_weather.*

const val TAG = "MainActivity"

class DailyWeatherFragment : Fragment(R.layout.daily_weather_scrolview) {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    val all_weather_data = MutableLiveData<CurrentWeather>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val cName = GET.getString("cityName", "moscow")
        //hourly_item_layout_tv.text = cName
        viewModel.refreshForecastData(cName!!)

        //val latCityName = GET.getString("lat", "55.7522")
        //val lonCityName = GET.getString("lon", "37.6156")
        //val cityName = GET.getString("cityName", "moscow")
        //hourly_weather_tv.setText(cName)
        //viewModel.refreshAllDataDaily(latCityName!!,lonCityName!!)

        getLiveDataDaily()

        /*
        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //var cName = GET.getString("cityName", "moscow")?.toLowerCase()
        //val cName = GET.getString("cityName", "moscow")
        //edt_city_name.setText(cName)
        //viewModel.refreshData(cName!!)

        getLiveDataDaily()
        initFields()

        swipe_refresh_layout.setOnRefreshListener {
            ll_data.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            //var cityName = GET.getString("cityName", cName)?.toLowerCase()
            //val cityName = GET.getString("cityName", cName)
            //edt_city_name.setText(cityName)
            //viewModel.refreshData(cityName!!)
            //swipe_refresh_layout.isRefreshing = false
        }

        img_search_city.setOnClickListener {
            val cityName = edt_city_name.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            getLiveDataDaily()
            Log.i(TAG, "onCreate: " + cityName)
        }

         */

    }//onCreate

    override fun onResume() {
        super.onResume()

        back_from_daily_iv.setOnClickListener {
            restartActivity()
        }
    }

    private fun getLiveDataDaily() {

        viewModel.forecast_weather_data.observe(this, Observer { data ->
            data?.let {
                data_daily_ll.visibility = View.VISIBLE

                daily_item_layout_tv.text = data.city.name
                tv_date_time3.text = data.list[9].dtTxt
                tv_temperature3.text = data.list[9].main.temp.toString() + "°C"
                tv_humidity3.text = data.list[9].main.humidity.toString() + "%"
                tv_wind_speed3.text = data.list[9].wind.speed.toString()

                tv_date_time3_2.text = data.list[17].dtTxt
                tv_temperature3_2.text = data.list[17].main.temp.toString() + "°C"
                tv_humidity3_2.text = data.list[17].main.humidity.toString() + "%"
                tv_wind_speed3_2.text = data.list[17].wind.speed.toString()

                tv_date_time3_3.text = data.list[25].dtTxt
                tv_temperature3_3.text = data.list[25].main.temp.toString() + "°C"
                tv_humidity3_3.text = data.list[25].main.humidity.toString() + "%"
                tv_wind_speed3_3.text = data.list[25].wind.speed.toString()

                tv_date_time3_4.text = data.list[33].dtTxt
                tv_temperature3_4.text = data.list[33].main.temp.toString() + "°C"
                tv_humidity3_4.text = data.list[33].main.humidity.toString() + "%"
                tv_wind_speed3_4.text = data.list[33].wind.speed.toString()

                /*
                tv_date_time3_5.text = data.list[41].dtTxt
                tv_temperature3_5.text = data.list[41].main.temp.toString() + "°C"
                tv_humidity3_5.text = data.list[41].main.humidity.toString() + "%"
                tv_wind_speed3_5.text = data.list[41].wind.speed.toString()

                tv_date_time3_6.text = data.list[49].dtTxt
                tv_temperature3_6.text = data.list[49].main.temp.toString() + "°C"
                tv_humidity3_6.text = data.list[49].main.humidity.toString() + "%"
                tv_wind_speed3_6.text = data.list[49].wind.speed.toString()

                tv_date_time3_7.text = data.list[57].dtTxt
                tv_temperature3_7.text = data.list[57].main.temp.toString() + "°C"
                tv_humidity3_7.text = data.list[57].main.humidity.toString() + "%"
                tv_wind_speed3_7.text = data.list[57].wind.speed.toString()

                 */

            }
        })
        viewModel.forecast_weather_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error3.visibility = View.VISIBLE
                    pb_loading3.visibility = View.GONE
                    data_daily_ll.visibility = View.GONE
                } else {
                    tv_error3.visibility = View.GONE
                }
            }
        })

        viewModel.forecast_weather_loading.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading3.visibility = View.VISIBLE
                    tv_error3.visibility = View.GONE
                    data_daily_ll.visibility = View.GONE
                } else {
                    pb_loading3.visibility = View.GONE
                }
            }
        })

    }//getLiveDataDaily

    /*
    private fun getLiveDataDaily() {

        viewModel.all_weather_data.observe(this, Observer { data ->
            data?.let {
                ll_data.visibility = View.VISIBLE

                tv_date_time3.text = data.minutely.toString()
                tv_temperature3.text = data.daily[0].temp.toString() + "°C"
                tv_humidity3.text = data.daily[0].humidity.toString() + "%"
                tv_wind_speed3.text = data.daily[0].windSpeed.toString()


            }
        })
        viewModel.weather_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    ll_data.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })

        viewModel.weather_loading.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    ll_data.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })

    }//getLiveDataDaily

    override fun onResume() {
        super.onResume()


    }

     */


}