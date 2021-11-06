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


class FavCityAdapter(
    //var itemList: MutableList<WeatherModel>,
    //private var listener: OnItemClickListener
) :RecyclerView.Adapter<FavCityAdapter.FavCityHolder>() {

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

        /*

        fun initFavCity(item:WeatherModel, action:OnItemClickListener){
            itemFavCityTitle.text = item.name
            itemView.setOnClickListener {
                action.OnItemClick(item,adapterPosition)
            }
        }

         */

    }//FavCityHolder

    /*
    interface OnItemClickListener{
        fun OnItemClick(item:WeatherModel,position: Int)
    }//OnItemClickListener

     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCityHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_city_item_layout,parent,false)
        //return FavCityHolder(view)
        val holder = FavCityHolder(view)

        //return FavCityHolder(view, itemClickListener)

        /*
        val holder = FavCityHolder(view,itemClickListener)

        holder.itemView.setOnClickListener {

            /*
            if(listFavCity.isEmpty()){
                FavCityFragment.listFavCity.add(listItem[holder.adapterPosition])
                restartActivity()
            } else {
                restartActivity()
            }

             */

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

         */

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(APP_ACTIVITY).get(MainViewModel::class.java)

        holder.itemView.setOnLongClickListener {

            when(cityNameList[holder.adapterPosition].id) {

                cityNameList[holder.adapterPosition].id -> {
                    sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
                    //replaceFragment(FavCityFragment())
                    deleteCities(cityNameList[holder.adapterPosition].id)
                    replaceFragment(FavCityFragment())
                    //updateCities(cnList)
                    //getCity()
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

                    //updateCities(cityNameList)
                    //getCity()
                    //sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
                    //deleteCities(cnList[holder.adapterPosition].id)

                    replaceFragment(ChangeCityFragment())
                }
            }
        }

        //return FavCityHolder(view)

        return holder

    }//onCreateViewHolder

    override fun onBindViewHolder(holder: FavCityHolder, position: Int) {

        //holder.itemFavCityTitle.text = listItem[position].name
        //holder.itemFavCityTitle.text = listFavCity[position].name
        holder.itemFavCityTitle.text = cityNameList[position].cityname
        //holder.initFavCity(itemList.get(position), listener)

    }//onBindViewHolder

    //override fun getItemCount(): Int = listItem.size
    //override fun getItemCount(): Int = listFavCity.size
    override fun getItemCount(): Int = cityNameList.size

    fun updateListItems(item:WeatherModel){



        if(!listFavCity.contains(item)) {
            listFavCity.add(item)
            //listItem.addAll(listOf(item))
            notifyItemInserted(listFavCity.size)
        }


        //itemList.add(item)
        //notifyItemInserted(itemList.size)


    }//updateListItems

    /*
    fun updateCities(items:ArrayList<CityNameModel>){
        this.cnList = items
        notifyItemInserted(cnList.size)
    }//updateCities

     */

    fun updateCities(items: MutableList<CityNameModel>){

            cityNameList = items
            notifyItemInserted(cityNameList.size)

    }//updateCities

    fun deleteCities(id:Int){

        if(id == null) return

            sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
            sqLiteHelper.deleteCityById(id)

    }//deleteCities

    /*
    fun deleteCities(id:Int){
        if(id == null) return

        val builder = AlertDialog.Builder(APP_ACTIVITY)
        builder.setMessage("Удалить город из избранного?")
        builder.setCancelable(true)

        builder.setPositiveButton("Да"){dialog, _ ->
            sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
            sqLiteHelper.deleteCityById(id)
            //getCity()
            dialog.dismiss()
        }
        builder.setNegativeButton("Нет"){dialog, _ ->
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }//deleteCities

     */


    fun getCity(){
        val cNameList = sqLiteHelper.getAllCityName()
        showToast("Выведен на экран : ${cNameList.size}")
        updateCities(cNameList)
    }



}//MainListAdapter