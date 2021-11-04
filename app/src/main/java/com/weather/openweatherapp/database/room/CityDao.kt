package com.weather.openweatherapp.database.room

import androidx.room.*

@Dao
interface CityDao {

    @Query("SELECT * FROM CITYNAMES ORDER BY ID DESC")
    fun getAllCity(): List<CityEntity>?

    @Insert
    fun insertCity(city: CityEntity?)

    @Delete
    fun deleteCity(city: CityEntity?)

    @Update
    fun updateCity(city: CityEntity?)

}//CityDao