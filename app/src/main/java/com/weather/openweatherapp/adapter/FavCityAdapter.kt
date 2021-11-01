package com.weather.openweatherapp.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weather.openweatherapp.R
import com.weather.openweatherapp.fragment.favcity.FavCityFragment
import com.weather.openweatherapp.fragment.favcity.FavCityFragment.Companion.listFavCity
import com.weather.openweatherapp.model.forecast.Forecast
import com.weather.openweatherapp.model.forecast.ForecastModel
import com.weather.openweatherapp.model.weather.WeatherModel
import com.weather.openweatherapp.utils.APP_ACTIVITY
import com.weather.openweatherapp.utils.restartActivity
import com.weather.openweatherapp.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fav_city_item_layout.view.*
import kotlinx.android.synthetic.main.hourly_weather_item.view.*


class FavCityAdapter :RecyclerView.Adapter<FavCityAdapter.FavCityHolder>() {

    private var listItem = mutableListOf<WeatherModel>()

    class FavCityHolder(view: View) : RecyclerView.ViewHolder(view){

        val itemFavCityTitle:TextView = view.fav_city_item_title_tv

    }//MainListHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCityHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_city_item_layout,parent,false)
        val holder = FavCityHolder(view)

        holder.itemView.setOnClickListener {


            if(listFavCity.isEmpty()){
                FavCityFragment.listFavCity.add(listItem[holder.adapterPosition])
                restartActivity()
            } else {
                restartActivity()
            }

            /*
            if(listItem[holder.adapterPosition].name == listItem[holder.adapterPosition].name){
                !FavCityFragment.listFavCity.add(listItem[holder.adapterPosition])
                //restartActivity()
            } else {
                FavCityFragment.listFavCity.add(listItem[holder.adapterPosition])
                restartActivity()
            }

             */

        }

        /*
        holder.itemView.setOnClickListener {

            //showToast("Click")
            //restartActivity()

            if (listItem[holder.adapterPosition].name.isEmpty()) {
                showToast("Выберите город")
            } else {
                restartActivity()
            }


        }

         */

        //return FavCityHolder(view)

        return holder

    }//onCreateViewHolder

    override fun onBindViewHolder(holder: FavCityHolder, position: Int) {

        //holder.itemFavCityTitle.text = listItem[position].name
        holder.itemFavCityTitle.text = listFavCity[position].name

    }//onBindViewHolder

    //override fun getItemCount(): Int = listItem.size
    override fun getItemCount(): Int = listFavCity.size

    fun updateListItems(item:WeatherModel){

        //listItem.add(item)
        //notifyItemInserted(listItem.size)

            listFavCity.add(item)
            notifyItemInserted(listFavCity.size)


    }//updateListItems

}//MainListAdapter