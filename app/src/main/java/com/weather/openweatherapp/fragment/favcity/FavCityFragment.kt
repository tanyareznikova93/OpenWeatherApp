package com.weather.openweatherapp.fragment.favcity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.openweatherapp.R
import com.weather.openweatherapp.utils.APP_ACTIVITY
//import com.weather.openweatherapp.utils.replaceActivity
import com.weather.openweatherapp.utils.restartActivity
import kotlinx.android.synthetic.main.fragment_fav_city.*


class FavCityFragment : Fragment(R.layout.fragment_fav_city) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        back_iv_from_fav_city.setOnClickListener {
            restartActivity()
        }
    }


}