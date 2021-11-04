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
        private const val DATABASE_NAME = "citydb.db"
        private const val TABLE_CITY = "tbl_city"
        private const val ID = "id"
        private const val CITYNAME = "cityname"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableCity = ("CREATE TABLE " + TABLE_CITY + "("
                + ID + " INTEGER PRIMARY KEY," + CITYNAME + " TEXT"
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
        val selectQuery = "SELECT  * FROM $TABLE_CITY"
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

}//SQLiteHelper