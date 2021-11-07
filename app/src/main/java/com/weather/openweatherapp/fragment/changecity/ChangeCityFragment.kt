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
import kotlinx.android.synthetic.main.fragment_change_city.*

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

        //initRecyclerView()
        //APP_ACTIVITY.getLiveData()
        //APP_ACTIVITY.getLiveDataHourly()
        //APP_ACTIVITY.getLiveDataDaily()

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

        /*
        swipe_refresh_fav_city.setOnRefreshListener {
            city_recycler_view.visibility = View.GONE
            tv_error_fav_city.visibility = View.GONE
            pb_loading_fav_city.visibility = View.GONE

            val cityName = GET.getString("cityName", cName)
            edt_city_name_fav_city.setText(cityName)
            //fav_city_item_title_tv.text.toString()
            viewModel.refreshDataForFavCity(cityName!!)
            swipe_refresh_fav_city.isRefreshing = false
        }

        img_search_fav_city.setOnClickListener {
            val cityName = edt_city_name_fav_city.text.toString()
            //cityName = fav_city_item_title_tv.text.toString()
            //fav_city_item_title_tv.text = cityName
            SET.putString("cityName", cityName)
            //SET.putString("cityName", cityName2)
            SET.apply()
            //if(listFavCity.isEmpty()) showToast("Добавьте город")
            //else replaceFragment(CreateGroupFragment(listFavCity))
            //viewModel.refreshData(cityName)
            //cityName = listItemFavCity.name
            //mAdapter.updateListItems(listItemFavCity)
            viewModel.refreshDataForFavCity(cityName)
            //viewModel.refreshForecastData(cityName)
            //viewModel.refreshForecastData2(cityName)
            initRecyclerView()
            //getLiveDataFavCity()
            //getLiveData()
            //getLiveDataHourly()
            //getLiveDataDaily()
            Log.i(com.weather.openweatherapp.view.TAG, "onCreate: " + cityName)
            //edt_city_name_fav_city.clearComposingText()

        }

         */


        image_btn_current_change_city_iv.setOnClickListener {
                replaceFragment(FavCityFragment())
        }

    }//onViewCreated

    private fun initRecyclerView() {

        mRecyclerView = hourly_recycler_view_current_change_city
        mAdapter = HourlyWeatherAdapter()
        mRecyclerView.adapter = mAdapter
        mLayoutManager = LinearLayoutManager(APP_ACTIVITY)
        //Concat Adapter
        /*
        val arrayList = arrayListOf<ChangeCityAdapter>()
        for(favCityItem in listFavCity){
            mAdapter = ChangeCityAdapter()
            arrayList.add(mAdapter)
        }


        //mAdapter = HourlyWeatherAdapter()

        val concatAdapterConfig = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()

        val concatAdapter = ConcatAdapter(concatAdapterConfig, arrayList)
        mLayoutManager = LinearLayoutManager(APP_ACTIVITY)
        mRecyclerView.adapter = concatAdapter

         */

    }//initRecyclerView

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
                tv_degree_current_change_city.text = data.main.temp.toString() + "°C"
                tv_humidity_current_change_city.text = data.main.humidity.toString() + "%"
                tv_wind_speed_current_change_city.text = data.wind.speed.toString() + "м/с"

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

    private fun getLiveDataDailyForChangeCity() {

        viewModel.forecast_weather_data2.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                data_daily_ll_current_change_city.visibility = View.VISIBLE

                //daily_item_layout_tv.text = data.city.name
                tv_date_time_daily_current_change_city.text = data.list[8].dtTxt + " |"
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[8].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_current_change_city)
                tv_temperature_daily_current_change_city.text = data.list[8].main.temp.toString() + "°C"
                tv_temperature_wind_daily_current_change_city.text = data.list[8].wind.speed.toString() + " м/с"

                data_daily_ll2_current_change_city.visibility = View.VISIBLE

                tv_date_time_daily_2_current_change_city.text = data.list[16].dtTxt + " |"
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[16].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_2_current_change_city)
                tv_temperature_daily_2_current_change_city.text = data.list[16].main.temp.toString() + "°C"
                tv_temperature_wind_daily_2_current_change_city.text = data.list[16].wind.speed.toString() + " м/с"

                data_daily_ll3_current_change_city.visibility = View.VISIBLE

                tv_date_time_daily_3_current_change_city.text = data.list[24].dtTxt + " |"
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[24].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_3_current_change_city)
                tv_temperature_daily_3_current_change_city.text = data.list[24].main.temp.toString() + "°C"
                tv_temperature_wind_daily_3_current_change_city.text = data.list[24].wind.speed.toString() + " м/с"

                data_daily_ll4_current_change_city.visibility = View.VISIBLE

                tv_date_time_daily_4_current_change_city.text = data.list[32].dtTxt + " |"
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.list[32].weather.get(0).icon + "@2x.png")
                    .into(img_weather_pictures_daily_4_current_change_city)
                tv_temperature_daily_4_current_change_city.text = data.list[32].main.temp.toString() + "°C"
                tv_temperature_wind_daily_4_current_change_city.text = data.list[32].wind.speed.toString() + " м/с"

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