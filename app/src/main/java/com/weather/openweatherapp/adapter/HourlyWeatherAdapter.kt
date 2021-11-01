package com.weather.openweatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weather.openweatherapp.R
import com.weather.openweatherapp.model.forecast.Forecast
import com.weather.openweatherapp.model.forecast.ForecastModel
import com.weather.openweatherapp.utils.APP_ACTIVITY
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.hourly_weather_item.view.*


class HourlyWeatherAdapter :RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherHolder>() {

    //private var listItem = mutableListOf<ForecastModel>()
    private var listItem = mutableListOf<ForecastModel>()

    class HourlyWeatherHolder(view: View) : RecyclerView.ViewHolder(view){

        val itemDateTime:TextView = view.tv_date_time_hourly
        val itemImage:ImageView = view.img_weather_pictures_hourly
        val itemTemperature:TextView = view.tv_temperature_hourly

    }//MainListHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_item,parent,false)
        //val holder = HourlyWeatherHolder(view)
        //holder.itemView
        return HourlyWeatherHolder(view)

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

        //val nListItem = listItem[position]
        //val nList = nListItem.list[position]

        holder.itemDateTime.text = listItem[position].list[position].dtTxt
        ///val url = nList.weather.get(0).icon + "@2x.png"
        /*
        Glide.with(holder.itemImage)
            .load("https://openweathermap.org/img/wn/" + listItem[position].list[position].weather.get(0).icon + "@2x.png")
            .into(holder.itemImage)

         */
        Glide.with(holder.itemImage)
            .load("https://openweathermap.org/img/wn/" + listItem[position].list[position].weather.get(0).icon + "@2x.png")
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.itemImage);
        holder.itemTemperature.text = listItem[position].list[position].main.temp.toString() + "°C"

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