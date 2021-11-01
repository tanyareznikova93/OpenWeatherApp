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
//import com.weather.openweatherapp.fragment.current.CurrentWeatherFragment
import com.weather.openweatherapp.fragment.favcity.FavCityFragment
import com.weather.openweatherapp.utils.APP_ACTIVITY
import com.weather.openweatherapp.utils.replaceFragment
import com.weather.openweatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.data_daily_ll
import kotlinx.android.synthetic.main.hourly_weather_item.*

//import kotlinx.android.synthetic.main.fragment_current_weather.*

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

        //initFields()

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //var cName = GET.getString("cityName", "moscow")?.toLowerCase()

        val cName = GET.getString("cityName", "moscow")
        edt_city_name.setText(cName)
        viewModel.refreshData(cName!!)
        //viewModel.refreshDataForFavCity(cName!!)
        viewModel.refreshForecastData(cName!!)
        viewModel.refreshForecastData2(cName!!)


        //val cLatName = GET.getString("lat", "55.7522")
        //val cLonName = GET.getString("lon", "37.6156")
        //setCity()
        //edt_city_name.setText(cName)
        //viewModel.refreshAllWeatherData(cName!!)

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
            //tv_error_hourly.visibility = View.GONE
            //pb_loading_hourly.visibility = View.GONE
            //tv_error_daily.visibility = View.GONE
            //pb_loading_daily.visibility = View.GONE

            //var cityName = GET.getString("cityName", cName)?.toLowerCase()
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
        }

/*
        hourly_ib.setOnClickListener {
            replaceFragment(HourlyWeatherFragment())
        }
        daily_ib.setOnClickListener {
            replaceFragment(DailyWeatherFragment())
        }

 */
        main_add_fav_city_iv.setOnClickListener {
            replaceFragment(FavCityFragment())
        }


    }//onCreate


    override fun onResume() {
        super.onResume()
    }//onResume

    fun initRecyclerView(){

            //mAdapter = HourlyWeatherAdapter()
            //mRecyclerView.adapter = mAdapter
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
                   //tv_lat.text = data.coord.lat.toString()
                   //tv_lon.text = data.coord.lon.toString()

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
                //notifyDataSetChanged()

                //val nData = data.list[1]
                //val nDataList = nData.main

                //mRecyclerView = hourly_recycler_view
                //mAdapter = HourlyWeatherAdapter()
                //mRecyclerView.layoutManager = LinearLayoutManager(APP_ACTIVITY)

                /*

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

                 */

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

                //daily_item_layout_tv.text = data.city.name
                tv_date_time_daily.text = data.list[8].dtTxt + " |"
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[8].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily)
                tv_temperature_daily.text = data.list[8].main.temp.toString() + "°C"
                tv_temperature_wind_daily.text = data.list[8].wind.speed.toString() + " м/с"

                data_daily_ll2.visibility = View.VISIBLE

                tv_date_time_daily_2.text = data.list[16].dtTxt + " |"
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[16].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_2)
                tv_temperature_daily_2.text = data.list[16].main.temp.toString() + "°C"
                tv_temperature_wind_daily_2.text = data.list[16].wind.speed.toString() + " м/с"

                data_daily_ll3.visibility = View.VISIBLE

                tv_date_time_daily_3.text = data.list[24].dtTxt + " |"
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[24].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_3)
                tv_temperature_daily_3.text = data.list[24].main.temp.toString() + "°C"
                tv_temperature_wind_daily_3.text = data.list[24].wind.speed.toString() + " м/с"

                data_daily_ll4.visibility = View.VISIBLE

                tv_date_time_daily_4.text = data.list[32].dtTxt + " |"
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[32].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_4)
                tv_temperature_daily_4.text = data.list[32].main.temp.toString() + "°C"
                tv_temperature_wind_daily_4.text = data.list[32].wind.speed.toString() + " м/с"

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


     /*
       private fun getLiveDataFuture() {

           viewModel.weather_data.observe(this, Observer { data ->
               data?.let {
                   ll_data.visibility = View.VISIBLE

                   /*
                   tv_date_time2.text = data.minutely.toString()
                   tv_temperature2.text = data.hourly[0].temp.toString() + "°C"
                   tv_humidity2.text = data.hourly[0].humidity.toString() + "%"
                   tv_wind_speed2.text = data.hourly[0].windSpeed.toString()

                   tv_date_time3.text = data.minutely[].toString()
                   tv_temperature3.text = data.hourly[1].temp.toString() + "°C"
                   tv_humidity3.text = data.hourly[1].humidity.toString() + "%"
                   tv_wind_speed3.text = data.hourly[1].windSpeed.toString()

                    */

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

       }//getLiveDataFuture

     */

}//MainActivity