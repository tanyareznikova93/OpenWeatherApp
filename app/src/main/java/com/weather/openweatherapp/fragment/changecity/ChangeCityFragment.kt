package com.weather.openweatherapp.fragment.changecity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weather.openweatherapp.R
import com.weather.openweatherapp.adapter.HourlyWeatherAdapter
import com.weather.openweatherapp.databinding.FragmentChangeCityBinding
import com.weather.openweatherapp.fragment.favcity.FavCityFragment
import com.weather.openweatherapp.model.weather.WeatherModel
import com.weather.openweatherapp.utils.*
import com.weather.openweatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_change_city.*
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "ChangeCityFragment"

class ChangeCityFragment : Fragment(R.layout.fragment_change_city) {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private lateinit var binding: FragmentChangeCityBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: HourlyWeatherAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private var listItem = mutableListOf<WeatherModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }//onCreate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChangeCityBinding.bind(view)

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val cName = GET.getString("cityName", "moscow")
        //edt_city_name_fav_city.setText(cName)
        tv_city_name_current_change_city.text = cName.toString()
        //fav_city_item_title_tv.setText(cName)
        viewModel.refreshData(cName!!)
        //viewModel.refreshDataForFavCity(listItem[0].name)
        viewModel.refreshForecastData(cName!!)
        viewModel.refreshForecastData2(cName!!)

        initRecyclerView()
        getLiveDataCurrentForChangeCity()
        getLiveDataHourlyForChangeCity()
        getLiveDataDailyForChangeCity()

        image_btn_current_change_city_iv.setOnClickListener {
                replaceFragment(FavCityFragment())
        }

    }//onViewCreated

    private fun initRecyclerView() {

        mRecyclerView = hourly_recycler_view_current_change_city
        mAdapter = HourlyWeatherAdapter()
        mRecyclerView.adapter = mAdapter
        mLayoutManager = LinearLayoutManager(APP_ACTIVITY)

    }//initRecyclerView

    //current weather for the selected city
    private fun getLiveDataCurrentForChangeCity() {

        viewModel.weather_data.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                ll_data_current_change_city.visibility = View.VISIBLE

                //tv_city_code.text = data.sys.country.toString()
                tv_city_name_current_change_city.text = data.name.toString()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_current_change_city)
                tv_weather_description_current_change_city.text = data.weather.get(0).description
                tv_degree_current_change_city.text = data.main.temp.toString() + "??C"
                tv_humidity_current_change_city.text = data.main.humidity.toString() + "%"
                tv_wind_speed_current_change_city.text = data.wind.speed.toString() + "??/??"

            }
        })

        viewModel.weather_error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    tv_error_current_change_city.visibility = View.VISIBLE
                    pb_loading_current_change_city.visibility = View.GONE
                    ll_data_current_change_city.visibility = View.GONE
                } else {
                    tv_error_current_change_city.visibility = View.GONE
                }
            }
        })

        viewModel.weather_loading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading_current_change_city.visibility = View.VISIBLE
                    tv_error_current_change_city.visibility = View.GONE
                    ll_data_current_change_city.visibility = View.GONE
                } else {
                    pb_loading_current_change_city.visibility = View.GONE
                }
            }
        })

    }//getLiveDataCurrentForChangeCity

    //hourly weather for the selected city
    private fun getLiveDataHourlyForChangeCity() {

        viewModel.forecast_weather_data.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                hourly_recycler_view_current_change_city.visibility = View.VISIBLE

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

        viewModel.forecast_weather_error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    tv_error_hourly_current_change_city.visibility = View.VISIBLE
                    pb_loading_hourly_current_change_city.visibility = View.GONE
                    hourly_recycler_view_current_change_city.visibility = View.GONE
                } else {
                    tv_error_hourly_current_change_city.visibility = View.GONE
                }
            }
        })

        viewModel.forecast_weather_loading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading_hourly_current_change_city.visibility = View.VISIBLE
                    tv_error_hourly_current_change_city.visibility = View.GONE
                    hourly_recycler_view_current_change_city.visibility = View.GONE
                } else {
                    pb_loading_hourly_current_change_city.visibility = View.GONE
                }
            }
        })


    }//getLiveDataHourlyForChangeCity

    //daily weather for the selected city
    private fun getLiveDataDailyForChangeCity() {

        viewModel.forecast_weather_data2.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                data_daily_ll_current_change_city.visibility = View.VISIBLE

                //daily_item_layout_tv.text = data.city.name
                //tv_date_time_daily_current_change_city.text = data.list[8].dtTxt + " |"
                val date:Long = data.list[8].dt.toLong()
                tv_date_time_daily_current_change_city.text = SimpleDateFormat("EEE, d  MMM", Locale.getDefault())
                    .format(Date(date*1000)).toUpperCase()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[8].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_current_change_city)
                tv_temperature_daily_current_change_city.text = data.list[8].main.temp.toString() + "??C"
                tv_temperature_wind_daily_current_change_city.text = data.list[8].wind.speed.toString() + " ??/??"

                data_daily_ll2_current_change_city.visibility = View.VISIBLE

                //tv_date_time_daily_2_current_change_city.text = data.list[16].dtTxt + " |"
                val date2:Long = data.list[16].dt.toLong()
                tv_date_time_daily_2_current_change_city.text = SimpleDateFormat("EEE, d  MMM", Locale.getDefault())
                    .format(Date(date2*1000)).toUpperCase()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[16].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_2_current_change_city)
                tv_temperature_daily_2_current_change_city.text = data.list[16].main.temp.toString() + "??C"
                tv_temperature_wind_daily_2_current_change_city.text = data.list[16].wind.speed.toString() + " ??/??"

                data_daily_ll3_current_change_city.visibility = View.VISIBLE

                //tv_date_time_daily_3_current_change_city.text = data.list[24].dtTxt + " |"
                val date3:Long = data.list[24].dt.toLong()
                tv_date_time_daily_3_current_change_city.text = SimpleDateFormat("EEE, d  MMM", Locale.getDefault())
                    .format(Date(date3*1000)).toUpperCase()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[24].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_3_current_change_city)
                tv_temperature_daily_3_current_change_city.text = data.list[24].main.temp.toString() + "??C"
                tv_temperature_wind_daily_3_current_change_city.text = data.list[24].wind.speed.toString() + " ??/??"

                data_daily_ll4_current_change_city.visibility = View.VISIBLE

                //tv_date_time_daily_4_current_change_city.text = data.list[32].dtTxt + " |"
                val date4:Long = data.list[32].dt.toLong()
                tv_date_time_daily_4_current_change_city.text = SimpleDateFormat("EEE, d  MMM", Locale.getDefault())
                    .format(Date(date4*1000)).toUpperCase()
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[32].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_4_current_change_city)
                tv_temperature_daily_4_current_change_city.text = data.list[32].main.temp.toString() + "??C"
                tv_temperature_wind_daily_4_current_change_city.text = data.list[32].wind.speed.toString() + " ??/??"

            }
        })
        viewModel.forecast_weather_error2.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) {
                    tv_error_daily_current_change_city.visibility = View.VISIBLE
                    pb_loading_daily_current_change_city.visibility = View.GONE
                    data_daily_ll_current_change_city.visibility = View.GONE
                    data_daily_ll2_current_change_city.visibility = View.GONE
                    data_daily_ll3_current_change_city.visibility = View.GONE
                    data_daily_ll4_current_change_city.visibility = View.GONE
                } else {
                    tv_error_daily_current_change_city.visibility = View.GONE
                }
            }
        })

        viewModel.forecast_weather_loading2.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading_daily_current_change_city.visibility = View.VISIBLE
                    tv_error_daily_current_change_city.visibility = View.GONE
                    data_daily_ll_current_change_city.visibility = View.GONE
                    data_daily_ll2_current_change_city.visibility = View.GONE
                    data_daily_ll3_current_change_city.visibility = View.GONE
                    data_daily_ll4_current_change_city.visibility = View.GONE
                } else {
                    pb_loading_daily_current_change_city.visibility = View.GONE
                }
            }
        })

    }//getLiveDataDailyForChangeCity


}//ChangeCityFragment