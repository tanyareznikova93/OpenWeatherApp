package com.weather.openweatherapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.weather.openweatherapp.model.weather.WeatherModel

//Позволяет преобразовывать старый список в новый без полной пересборки RecyclerView
class DiffUtilCallback(

    private val oldList:List<WeatherModel>,
    private val newList:List<WeatherModel>

):DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}//DiffUtilCallback