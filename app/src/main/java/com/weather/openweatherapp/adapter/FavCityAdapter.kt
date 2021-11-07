package com.weather.openweatherapp.adapter

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weather.openweatherapp.R
import com.weather.openweatherapp.database.CityNameModel
import com.weather.openweatherapp.database.SQLiteHelper
import com.weather.openweatherapp.fragment.changecity.ChangeCityFragment
import com.weather.openweatherapp.fragment.favcity.FavCityFragment
import com.weather.openweatherapp.fragment.favcity.FavCityFragment.Companion.cityNameList
import com.weather.openweatherapp.fragment.favcity.FavCityFragment.Companion.listFavCity
import com.weather.openweatherapp.fragment.favcity.TAG
import com.weather.openweatherapp.model.forecast.Forecast
import com.weather.openweatherapp.model.forecast.ForecastModel
import com.weather.openweatherapp.model.weather.WeatherModel
import com.weather.openweatherapp.utils.*
import com.weather.openweatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fav_city_item_layout.view.*
import kotlinx.android.synthetic.main.fragment_fav_city.*
import kotlinx.android.synthetic.main.hourly_weather_item.view.*
import java.util.ArrayList


class FavCityAdapter :RecyclerView.Adapter<FavCityAdapter.FavCityHolder>() {

    private var listItem = mutableListOf<WeatherModel>()
    private var cnList: ArrayList<CityNameModel> = ArrayList()
    //private var viewModel = MainViewModel()
    //private val changeCityFragment = ChangeCityFragment()
    private lateinit var viewModel: MainViewModel
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor
    private lateinit var sqLiteHelper: SQLiteHelper

    class FavCityHolder(view: View) : RecyclerView.ViewHolder(view) {

        val itemFavCityTitle:TextView = view.fav_city_item_title_tv

    }//FavCityHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCityHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_city_item_layout,parent,false)
        val holder = FavCityHolder(view)

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(APP_ACTIVITY).get(MainViewModel::class.java)

        holder.itemView.setOnLongClickListener {

            when(cityNameList[holder.adapterPosition].id) {

                cityNameList[holder.adapterPosition].id -> {
                    sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
                    deleteCities(cityNameList[holder.adapterPosition].id)
                    replaceFragment(FavCityFragment())
                    return@setOnLongClickListener true
                }

                else -> return@setOnLongClickListener false

            }
        }

        holder.itemView.setOnClickListener {

            when (cityNameList[holder.adapterPosition].cityname) {
                cityNameList[holder.adapterPosition].cityname -> {

                    val cityName = cityNameList[holder.adapterPosition].cityname
                    SET.putString("cityName", cityName)
                    SET.apply()

                    viewModel.refreshData(cityName)
                    viewModel.refreshDataForFavCity(cityName)
                    viewModel.refreshForecastData(cityName)
                    viewModel.refreshForecastData2(cityName)

                    replaceFragment(ChangeCityFragment())
                }
            }
        }

        return holder

    }//onCreateViewHolder

    override fun onBindViewHolder(holder: FavCityHolder, position: Int) {

        holder.itemFavCityTitle.text = cityNameList[position].cityname

    }//onBindViewHolder

    override fun getItemCount(): Int = cityNameList.size

    fun updateListItems(item:WeatherModel){

        if(!listFavCity.contains(item)) {
            listFavCity.add(item)
            //listItem.addAll(listOf(item))
            notifyItemInserted(listFavCity.size)
        }

    }//updateListItems

    fun updateCities(items: MutableList<CityNameModel>){

            cityNameList = items
            notifyItemInserted(cityNameList.size)

    }//updateCities

    fun deleteCities(id:Int){

        if(id == null) return

            sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
            sqLiteHelper.deleteCityById(id)

    }//deleteCities

    fun getCity(){
        val cNameList = sqLiteHelper.getAllCityName()
        showToast("Выведен на экран : ${cNameList.size}")
        updateCities(cNameList)
    }

}//MainListAdapter