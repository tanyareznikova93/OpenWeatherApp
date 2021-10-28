package com.weather.openweatherapp.fragment.daily

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.weather.openweatherapp.R
import com.weather.openweatherapp.api.WeatherAPIService
//import com.weather.openweatherapp.fragment.current.CurrentWeatherFragment
import com.weather.openweatherapp.model.CurrentWeather
import com.weather.openweatherapp.utils.APP_ACTIVITY
//import com.weather.openweatherapp.utils.replaceActivity
import com.weather.openweatherapp.utils.replaceFragment
import com.weather.openweatherapp.utils.restartActivity
import com.weather.openweatherapp.view.MainActivity
import com.weather.openweatherapp.viewmodel.MainViewModel
//import com.weather.openweatherapp.viewmodel.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.daily_item_layout.*
//import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.android.synthetic.main.fragment_daily_weather.*
import kotlinx.android.synthetic.main.fragment_fav_city.*

const val TAG = "MainActivity"

class DailyWeatherFragment : Fragment(R.layout.fragment_daily_weather) {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    val all_weather_data = MutableLiveData<CurrentWeather>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    private fun initFields() {

        //back_from_daily_iv.setOnClickListener{ (activity as MainActivity).replaceFragment(CurrentWeatherFragment()) }
        //back_from_daily_iv.setOnClickListener{ replaceFragment(CurrentWeatherFragment()) }
        //back_from_daily_iv.setOnClickListener{ APP_ACTIVITY.replaceActivity(MainActivity()) }

    }



}