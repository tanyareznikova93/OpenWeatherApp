package com.weather.openweatherapp.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weather.openweatherapp.R
import com.weather.openweatherapp.adapter.HourlyWeatherAdapter
import com.weather.openweatherapp.databinding.ActivityMainBinding
import com.weather.openweatherapp.fragment.changecity.ChangeCityFragment
//import com.weather.openweatherapp.fragment.current.CurrentWeatherFragment
import com.weather.openweatherapp.fragment.favcity.FavCityFragment
import com.weather.openweatherapp.utils.APP_ACTIVITY
import com.weather.openweatherapp.utils.asDate
import com.weather.openweatherapp.utils.hideKeyboard
import com.weather.openweatherapp.utils.replaceFragment
import com.weather.openweatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.data_daily_ll
import kotlinx.android.synthetic.main.hourly_weather_item.*
import java.util.*

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: HourlyWeatherAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //val cName = GET.getString("cityName", "moscow")?.toLowerCase()
        //val cName = GET.getString("cityName", "moscow")?.lowercase(Locale.getDefault())
        val cName = GET.getString("cityName", "moscow")
        edt_city_name.setText(cName)
        viewModel.refreshData(cName!!)
        //viewModel.refreshDataForFavCity(cName!!)
        viewModel.refreshForecastData(cName!!)
        viewModel.refreshForecastData2(cName!!)

        initRecyclerView()
        getLiveData()
        getLiveDataHourly()
        getLiveDataDaily()
        //getLiveDataFuture()

        swipe_refresh_layout.setOnRefreshListener {
            ll_data.visibility = View.GONE
            hourly_recycler_view.visibility = View.GONE
            data_daily_ll.visibility = View.GONE
            data_daily_ll2.visibility = View.GONE
            data_daily_ll3.visibility = View.GONE
            data_daily_ll4.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            val cityName = GET.getString("cityName", cName)
            edt_city_name.setText(cityName)
            viewModel.refreshData(cityName!!)
            //viewModel.refreshDataForFavCity(cityName!!)
            viewModel.refreshForecastData(cityName!!)
            viewModel.refreshForecastData2(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }

        img_search_city.setOnClickListener {
            val cityName = edt_city_name.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            //viewModel.refreshDataForFavCity(cityName)
            viewModel.refreshForecastData(cityName)
            viewModel.refreshForecastData2(cityName)
            initRecyclerView()
            getLiveData()
            getLiveDataHourly()
            getLiveDataDaily()
            Log.i(TAG, "onCreate: " + cityName)
            hideKeyboard()
        }

        main_add_fav_city_iv.setOnClickListener {
            replaceFragment(FavCityFragment())
        }


    }//onCreate


    override fun onResume() {
        super.onResume()
    }//onResume

    fun initRecyclerView(){
        mRecyclerView = hourly_recycler_view
        mAdapter = HourlyWeatherAdapter()
        mRecyclerView.adapter = mAdapter
        mLayoutManager = LinearLayoutManager(APP_ACTIVITY)
    }

    private fun getLiveData() {

           viewModel.weather_data.observe(this, Observer { data ->
               data?.let {
                   ll_data.visibility = View.VISIBLE

                   //tv_city_code.text = data.sys.country.toString()
                   tv_city_name.text = data.name.toString()
                   Glide.with(this)
                       .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                       .into(img_weather_pictures)
                   tv_weather_description.text = data.weather.get(0).description
                   tv_degree.text = data.main.temp.toString() + "°C"
                   tv_humidity.text = data.main.humidity.toString() + "%"
                   tv_wind_speed.text = data.wind.speed.toString() + "м/с"

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

       }//getLiveData

    private fun getLiveDataHourly() {

        viewModel.forecast_weather_data.observe(this, Observer { data ->
            data?.let {
                hourly_recycler_view.visibility = View.VISIBLE

                mAdapter.updateListItems(data)
                mAdapter.updateListItems(data)
                mAdapter.updateListItems(data)
                mAdapter.updateListItems(data)
                mAdapter.updateListItems(data)
                mAdapter.updateListItems(data)
                mAdapter.updateListItems(data)
                mAdapter.updateListItems(data)

            }
        })

        viewModel.forecast_weather_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error_hourly.visibility = View.VISIBLE
                    pb_loading_hourly.visibility = View.GONE
                    hourly_recycler_view.visibility = View.GONE
                } else {
                    tv_error_hourly.visibility = View.GONE
                }
            }
        })

        viewModel.forecast_weather_loading.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading_hourly.visibility = View.VISIBLE
                    tv_error_hourly.visibility = View.GONE
                    hourly_recycler_view.visibility = View.GONE
                } else {
                    pb_loading_hourly.visibility = View.GONE
                }
            }
        })


    }//getLiveDataHourly

    private fun getLiveDataDaily() {

        viewModel.forecast_weather_data2.observe(this, Observer { data ->
            data?.let {
                data_daily_ll.visibility = View.VISIBLE

                tv_date_time_daily.text = data.list[8].dtTxt
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[8].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily)
                tv_temperature_daily.text = data.list[8].main.temp.toString() + "°C"
                tv_temperature_wind_daily.text = data.list[8].wind.speed.toString() + " м/с"

                data_daily_ll2.visibility = View.VISIBLE

                tv_date_time_daily_2.text = data.list[16].dtTxt
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[16].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_2)
                tv_temperature_daily_2.text = data.list[16].main.temp.toString() + "°C"
                tv_temperature_wind_daily_2.text = data.list[16].wind.speed.toString() + " м/с"

                data_daily_ll3.visibility = View.VISIBLE

                tv_date_time_daily_3.text = data.list[24].dtTxt
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[24].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_3)
                tv_temperature_daily_3.text = data.list[24].main.temp.toString() + "°C"
                tv_temperature_wind_daily_3.text = data.list[24].wind.speed.toString() + " м/с"

                data_daily_ll4.visibility = View.VISIBLE

                tv_date_time_daily_4.text = data.list[32].dtTxt
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[32].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_4)
                tv_temperature_daily_4.text = data.list[32].main.temp.toString() + "°C"
                tv_temperature_wind_daily_4.text = data.list[32].wind.speed.toString() + " м/с"

            }
        })
        viewModel.forecast_weather_error2.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error_daily.visibility = View.VISIBLE
                    pb_loading_daily.visibility = View.GONE
                    data_daily_ll.visibility = View.GONE
                    data_daily_ll2.visibility = View.GONE
                    data_daily_ll3.visibility = View.GONE
                    data_daily_ll4.visibility = View.GONE
                } else {
                    tv_error_daily.visibility = View.GONE
                }
            }
        })

        viewModel.forecast_weather_loading2.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading_daily.visibility = View.VISIBLE
                    tv_error_daily.visibility = View.GONE
                    data_daily_ll.visibility = View.GONE
                    data_daily_ll2.visibility = View.GONE
                    data_daily_ll3.visibility = View.GONE
                    data_daily_ll4.visibility = View.GONE
                } else {
                    pb_loading_daily.visibility = View.GONE
                }
            }
        })

    }//getLiveDataDaily

}//MainActivity