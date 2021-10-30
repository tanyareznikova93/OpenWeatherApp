package com.weather.openweatherapp.fragment.hourly

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.annotations.JsonAdapter
import com.weather.openweatherapp.R
import com.weather.openweatherapp.adapter.HourlyWeatherAdapter
import com.weather.openweatherapp.model.forecast.ForecastModel
import com.weather.openweatherapp.utils.APP_ACTIVITY
import com.weather.openweatherapp.utils.restartActivity
import com.weather.openweatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fourly_weather_scrolview.*
import kotlinx.android.synthetic.main.fragment_hourly_weather.*
import kotlinx.android.synthetic.main.fragment_hourly_weather.back_from_hourly_iv
import kotlinx.android.synthetic.main.fragment_hourly_weather.hourly_item_layout_tv
import kotlinx.android.synthetic.main.fragment_hourly_weather.pb_loading2
import kotlinx.android.synthetic.main.fragment_hourly_weather.swipe_refresh_layout2
import kotlinx.android.synthetic.main.fragment_hourly_weather.tv_error2
import kotlinx.android.synthetic.main.hourly_weather_item.*
import kotlinx.android.synthetic.main.hourly_weather_item.data_hourly_ll
import kotlinx.android.synthetic.main.hourly_weather_item.tv_date_time2
import kotlinx.android.synthetic.main.hourly_weather_item.tv_humidity2
import kotlinx.android.synthetic.main.hourly_weather_item.tv_temperature2
import kotlinx.android.synthetic.main.hourly_weather_item.tv_wind_speed2

const val TAG = "MainActivity"
//const val TAG = "HourlyWeatherFragment"

class HourlyWeatherFragment : Fragment(R.layout.fourly_weather_scrolview) {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    //val all_weather_data = MutableLiveData<ForecastModel>()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: HourlyWeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val cName = GET.getString("cityName", "moscow")
        //hourly_item_layout_tv.text = cName
        viewModel.refreshForecastData(cName!!)

        //val adapter = JsonAdapter()

        //val latCityName = GET.getString("lat", "55.7522")
        //val lonCityName = GET.getString("lon", "37.6156")
        //val cityName = GET.getString("cityName", "moscow")
        //hourly_weather_tv.setText(cName)
        //viewModel.refreshAllDataHourly(latCityName!!,lonCityName!!)

        //var cName = GET.getString("cityName", "moscow")?.toLowerCase()
        //val cName = GET.getString("cityName", "moscow")
        //edt_city_name.setText(cName)
        //viewModel.refreshData(cName!!)

        getLiveDataHourly()
        //initFields()

/*
        swipe_refresh_layout2.setOnRefreshListener {
            data_hourly_ll.visibility = View.GONE
            tv_error2.visibility = View.GONE
            pb_loading2.visibility = View.GONE

            //var cityName = GET.getString("cityName", cName)?.toLowerCase()
            val cityName = GET.getString("cityName", cName)
            hourly_item_layout_tv.setText(cityName)
            viewModel.refreshForecastData(cityName!!)
            swipe_refresh_layout2.isRefreshing = false
        }

 */



        /*
        swipe_refresh_layout2.setOnRefreshListener {
            data_hourly_ll.visibility = View.GONE
            tv_error2.visibility = View.GONE
            pb_loading2.visibility = View.GONE

            //var cityName = GET.getString("cityName", cName)?.toLowerCase()
            val latCName = GET.getString("lat", "55.7522")
            val lonCName = GET.getString("lon", "37.6156")
            //val cityName = GET.getString("cityName", "moscow")
            //hourly_weather_tv.setText(cName)
            viewModel.refreshAllData(latCName!!,lonCName!!)
            swipe_refresh_layout2.isRefreshing = false
        }

         */

/*
        swipe_refresh_layout.setOnRefreshListener {
            data_hourly_ll.visibility = View.GONE
            //tv_error.visibility = View.GONE
            //pb_loading.visibility = View.GONE

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
            getLiveDataHourly()
            Log.i(TAG, "onCreate: " + cityName)
        }

 */

    }//onCreate


    private fun initFields() {
        mRecyclerView = hourly_recycler_view
        mAdapter = HourlyWeatherAdapter()

        mRecyclerView.layoutManager = LinearLayoutManager(APP_ACTIVITY)


    }//



    override fun onResume() {
        super.onResume()

        //getLiveDataHourly()
        //initFields()

        //mRecyclerView = hourly_recycler_view
        //mAdapter = HourlyWeatherAdapter()

        back_from_hourly_iv.setOnClickListener {
            restartActivity()
        }
    }//onResume


    private fun getLiveDataHourly() {

        viewModel.forecast_weather_data.observe(this, Observer { data ->
            data?.let {
                data_hourly_ll.visibility = View.VISIBLE

                //val nData = data.list[1]
                //val nDataList = nData.main

                //mRecyclerView = hourly_recycler_view
                //mAdapter = HourlyWeatherAdapter()
                //mRecyclerView.layoutManager = LinearLayoutManager(APP_ACTIVITY)

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[1].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_hourly)

                hourly_item_layout_tv.text = data.city.name
                tv_date_time2.text = data.list[1].dtTxt
                tv_temperature2.text = data.list[1].main.temp.toString() + "°C"
                tv_humidity2.text = data.list[1].main.humidity.toString() + "%"
                tv_wind_speed2.text = data.list[1].wind.speed.toString()

                //mAdapter.updateListItems(data)
                //mRecyclerView.adapter = mAdapter


                tv_date_time2_2.text = data.list[2].dtTxt
                tv_temperature2_2.text = data.list[2].main.temp.toString() + "°C"
                tv_humidity2_2.text = data.list[2].main.humidity.toString() + "%"
                tv_wind_speed2_2.text = data.list[2].wind.speed.toString()

                tv_date_time2_3.text = data.list[3].dtTxt
                tv_temperature2_3.text = data.list[3].main.temp.toString() + "°C"
                tv_humidity2_3.text = data.list[3].main.humidity.toString() + "%"
                tv_wind_speed2_3.text = data.list[3].wind.speed.toString()


                tv_date_time2_4.text = data.list[4].dtTxt
                tv_temperature2_4.text = data.list[4].main.temp.toString() + "°C"
                tv_humidity2_4.text = data.list[4].main.humidity.toString() + "%"
                tv_wind_speed2_4.text = data.list[4].wind.speed.toString()

               tv_date_time2_5.text = data.list[5].dtTxt
               tv_temperature2_5.text = data.list[5].main.temp.toString() + "°C"
               tv_humidity2_5.text = data.list[5].main.humidity.toString() + "%"
               tv_wind_speed2_5.text = data.list[5].wind.speed.toString()

               tv_date_time2_6.text = data.list[6].dtTxt
               tv_temperature2_6.text = data.list[6].main.temp.toString() + "°C"
               tv_humidity2_6.text = data.list[6].main.humidity.toString() + "%"
               tv_wind_speed2_6.text = data.list[6].wind.speed.toString()

               tv_date_time2_7.text = data.list[7].dtTxt
               tv_temperature2_7.text = data.list[7].main.temp.toString() + "°C"
               tv_humidity2_7.text = data.list[7].main.humidity.toString() + "%"
               tv_wind_speed2_7.text = data.list[7].wind.speed.toString()

               tv_date_time2_8.text = data.list[8].dtTxt
               tv_temperature2_8.text = data.list[8].main.temp.toString() + "°C"
               tv_humidity2_8.text = data.list[8].main.humidity.toString() + "%"
               tv_wind_speed2_8.text = data.list[8].wind.speed.toString()

            }
        })

        viewModel.forecast_weather_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error2.visibility = View.VISIBLE
                    pb_loading2.visibility = View.GONE
                    data_hourly_ll.visibility = View.GONE
                } else {
                    tv_error2.visibility = View.GONE
                }
            }
        })

        viewModel.forecast_weather_loading.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading2.visibility = View.VISIBLE
                    tv_error2.visibility = View.GONE
                    data_hourly_ll.visibility = View.GONE
                } else {
                    pb_loading2.visibility = View.GONE
                }
            }
        })


    }//getLiveDataHourly

/*
    private fun getLiveDataHourly() {

        viewModel.hourly_weather_data.observe(this, Observer { data ->
            data?.let {
                data_hourly_ll.visibility = View.VISIBLE

                //hourly_item_layout_tv.text = data.
                tv_date_time2.text = data.dt.toString()
                tv_temperature2.text = data.temp.toString() + "°C"
                tv_humidity2.text = data.humidity.toString() + "%"
                tv_wind_speed2.text = data.windSpeed.toString()
                //tv_lat2.text = data.
                //tv_lon2.text = data.lon.toString()
                mAdapter.updateListItems(data)
            }
        })
        viewModel.weather_error2.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error2.visibility = View.VISIBLE
                    pb_loading2.visibility = View.GONE
                    data_hourly_ll.visibility = View.GONE
                } else {
                    tv_error2.visibility = View.GONE
                }
            }
        })

        viewModel.weather_loading2.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading2.visibility = View.VISIBLE
                    tv_error2.visibility = View.GONE
                    data_hourly_ll.visibility = View.GONE
                } else {
                    pb_loading2.visibility = View.GONE
                }
            }
        })

    }//getLiveDataHourly

 */

}//