package com.weather.openweatherapp.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityEntity::class], version = 1)
abstract class RoomAppDb: RoomDatabase() {

    abstract fun cityDao(): CityDao?

    companion object {
        private var INSTANCE: RoomAppDb?= null

        fun getAppDatabase(context: Context): RoomAppDb? {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder<RoomAppDb>(
                    context.applicationContext,
                    RoomAppDb::class.java,
                    "CityNameDB")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }//getAppDatabase
    }
}//RoomAppDb