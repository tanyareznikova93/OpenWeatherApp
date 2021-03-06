package com.weather.openweatherapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.weather.openweatherapp.utils.showToast

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "fav_city_db.db"
        private const val TABLE_CITY = "fav_city_tbl"
        private const val ID = "id"
        private const val CITYNAME = "cityname"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableCity = ("CREATE TABLE " + TABLE_CITY + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CITYNAME + " TEXT NOT NULL UNIQUE"
                + ")")
        db?.execSQL(createTableCity)
    }//onCreate

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CITY")
        onCreate(db)
    }//onUpgrade

    fun insertCityName(cn:CityNameModel): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,cn.id)
        contentValues.put(CITYNAME,cn.cityname)

        val success = db.insert(TABLE_CITY, null, contentValues)
        db.close()
        return success

    }//InsertCityName


    fun getAllCityName(): ArrayList<CityNameModel>{

        val cnList:ArrayList<CityNameModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_CITY ORDER BY $CITYNAME ASC"
        //val selectQuery = "SELECT  * FROM " + TABLE_CITY
        val db = this.readableDatabase

        val cursor:Cursor?

        try {

            cursor = db.rawQuery(selectQuery,null)

        } catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var cityname:String

        if(cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                cityname = cursor.getString(cursor.getColumnIndexOrThrow("cityname"))
                val cn = CityNameModel(id = id, cityname = cityname)
                cnList.add(cn)
            }while (cursor.moveToNext())
            //cursor.close()
        }
        return cnList

    }//getAllCityName


    fun updateCity(cn: CityNameModel): Int {

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,cn.id)
        contentValues.put(CITYNAME,cn.cityname)

        val success = db.update(TABLE_CITY, contentValues, "id=" + cn.id, null)
        db.close()
        return success

    }//updateCity

    fun deleteCityById(id:Int): Int {

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,id)

        val success = db.delete(TABLE_CITY, "id=$id", null)
        //db!!.execSQL("DELETE FROM $TABLE_CITY")
        db.close()
        return success

    }//deleteCityById

}//SQLiteHelper