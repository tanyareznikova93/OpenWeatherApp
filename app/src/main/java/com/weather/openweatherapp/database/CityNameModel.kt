package com.weather.openweatherapp.database

import kotlin.random.Random


data class CityNameModel(
    val id: Int = getAutoID(),
    val cityname:String = ""
){
    companion object {
        fun getAutoID():Int{
            val random = Random.nextInt(100)
            //return random.nextInt(100)
            return random
        }
    }
}