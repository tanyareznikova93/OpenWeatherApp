package com.weather.openweatherapp.fragment.hourly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.openweatherapp.R
import com.weather.openweatherapp.utils.restartActivity
import kotlinx.android.synthetic.main.fragment_fav_city.*
import kotlinx.android.synthetic.main.fragment_hourly_weather.*


class HourlyWeatherFragment : Fragment(R.layout.fragment_hourly_weather) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }//onCreate

    override fun onResume() {
        super.onResume()

        back_from_hourly_iv.setOnClickListener {
            restartActivity()
        }
    }//onResume


}//