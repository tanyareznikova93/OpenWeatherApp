package com.weather.openweatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weather.openweatherapp.R
import com.weather.openweatherapp.model.alldata.Hourly
import com.weather.openweatherapp.model.forecast.ForecastModel
import com.weather.openweatherapp.utils.restartActivity
import kotlinx.android.synthetic.main.hourly_weather_item.view.*


class HourlyWeatherAdapter :RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherHolder>() {

    //private var listItem = mutableListOf<ForecastModel>()
    private var listItem = mutableListOf<ForecastModel>()

    class HourlyWeatherHolder(view: View) : RecyclerView.ViewHolder(view){

        val itemDateTime:TextView = view.tv_date_time2
        val itemTemperature:TextView = view.tv_temperature2
        val itemHumidity:TextView = view.tv_humidity2
        val itemWind:TextView = view.tv_wind_speed2

    }//MainListHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_item,parent,false)
        val holder = HourlyWeatherHolder(view)
        holder.itemView
        return holder

        /*
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_item, parent, false)


        return HourlyWeatherHolder(view)

         */
    }//onCreateViewHolder

    override fun onBindViewHolder(holder: HourlyWeatherHolder, position: Int) {

        /*
        holder.itemDateTime.text = listItem[position].list[position].dtTxt
        holder.itemTemperature.text = listItem[position].list[position].main.temp.toString()
        holder.itemHumidity.text = listItem[position].list[position].main.humidity.toString()
        holder.itemWind.text = listItem[position].list[position].wind.speed.toString()

         */

        val nListItem = listItem[position]
        val nList = nListItem.list[position]

        holder.itemDateTime.text = nList.dtTxt
        holder.itemTemperature.text = nList.main.temp.toString()
        holder.itemHumidity.text = nList.main.humidity.toString()
        holder.itemWind.text = nList.wind.speed.toString()

        /*
        holder.itemDateTime.text = listItem[position].list[position].dtTxt
        holder.itemTemperature.text = listItem[position].list[position].main.temp.toString()
        holder.itemHumidity.text = listItem[position].list[position].main.humidity.toString()
        holder.itemWind.text = listItem[position].list[position].wind.speed.toString()

         */
    }//onBindViewHolder

    override fun getItemCount(): Int = listItem.size

    fun updateListItems(item:ForecastModel){

        listItem.add(item)
        notifyItemInserted(listItem.size)

    }//updateListItems

}//MainListAdapter