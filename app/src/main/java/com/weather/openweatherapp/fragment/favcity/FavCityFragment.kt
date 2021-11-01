package com.weather.openweatherapp.fragment.favcity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weather.openweatherapp.R
import com.weather.openweatherapp.adapter.FavCityAdapter
import com.weather.openweatherapp.adapter.HourlyWeatherAdapter
import com.weather.openweatherapp.databinding.ActivityMainBinding
import com.weather.openweatherapp.databinding.FragmentFavCityBinding
import com.weather.openweatherapp.model.weather.WeatherModel
import com.weather.openweatherapp.utils.APP_ACTIVITY
//import com.weather.openweatherapp.utils.replaceActivity
import com.weather.openweatherapp.utils.restartActivity
import com.weather.openweatherapp.utils.showToast
import com.weather.openweatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fav_city_item_layout.*
import kotlinx.android.synthetic.main.fragment_fav_city.*

const val TAG = "MainActivity"

class FavCityFragment : Fragment(R.layout.fragment_fav_city) {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private lateinit var binding: FragmentFavCityBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FavCityAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }//onCreate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavCityBinding.bind(view)

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val cName = GET.getString("cityName", "moscow")
        edt_city_name_fav_city.setText(cName)
        //fav_city_item_title_tv.setText(cName)
        viewModel.refreshData(cName!!)
        viewModel.refreshDataForFavCity(cName!!)
        viewModel.refreshForecastData(cName!!)
        viewModel.refreshForecastData2(cName!!)

        initRecyclerView()
        getLiveDataFavCity()

        /*
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
            viewModel.refreshForecastData(cityName!!)
            viewModel.refreshForecastData2(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }

         */


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
            viewModel.refreshData(cityName)
            viewModel.refreshDataForFavCity(cityName)
            viewModel.refreshForecastData(cityName)
            viewModel.refreshForecastData2(cityName)
            initRecyclerView()
            getLiveDataFavCity()
            //getLiveData()
            //getLiveDataHourly()
            //getLiveDataDaily()
            Log.i(com.weather.openweatherapp.view.TAG, "onCreate: " + cityName)

        }

        add_fav_city_iv.setOnClickListener {
            if(cName.isEmpty()){
                showToast("Введите название города")
            } else {
                restartActivity()
            }
        }

    }//onViewCreated

    private fun initRecyclerView() {
        mRecyclerView = city_recycler_view
        mAdapter = FavCityAdapter()
        mRecyclerView.adapter = mAdapter
        mLayoutManager = LinearLayoutManager(APP_ACTIVITY)
    }//initRecyclerView

    override fun onResume() {
        super.onResume()

        back_iv_from_fav_city.setOnClickListener {
            restartActivity()
        }
    }//onResume


    private fun getLiveDataFavCity() {

        //viewLifecycleOwner
        viewModel.weather_data_fav_city.observe(APP_ACTIVITY, Observer { data ->
            data?.let {

                city_recycler_view.visibility = View.VISIBLE
                mAdapter.updateListItems(data)

            }
        })

        viewModel.weather_error_fav_city.observe(APP_ACTIVITY, Observer { error ->
            error?.let {
                if (error) {
                    tv_error_fav_city.visibility = View.VISIBLE
                    pb_loading_fav_city.visibility = View.GONE
                    city_recycler_view.visibility = View.GONE
                } else {
                    tv_error_fav_city.visibility = View.GONE
                }
            }
        })

        viewModel.weather_loading_fav_city.observe(APP_ACTIVITY, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading_fav_city.visibility = View.VISIBLE
                    tv_error_fav_city.visibility = View.GONE
                    city_recycler_view.visibility = View.GONE
                } else {
                    pb_loading_fav_city.visibility = View.GONE
                }
            }
        })

    }//getLiveDataFavCity


    companion object {
        val listFavCity = mutableListOf<WeatherModel>()
    }


}//FavCityFragment