package com.weather.openweatherapp.utils

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.weather.openweatherapp.R
import com.weather.openweatherapp.view.MainActivity
import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

//Функция показывает сообщение
fun showToast(message:String){
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

//Функция расширения для AppCompatActivity, позволяет запускать активити
fun restartActivity(){
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    //APP_ACTIVITY.finish()

}//restartActivity

//Функция расширения для AppCompatActivity, позволяет устанавливать фрагменты
fun replaceFragment(fragment: Fragment, addStack:Boolean = true){
    if(addStack){
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.data_container, fragment).commit()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.data_container, fragment).commit()
    }

}//replaceFragment

//Функция скрывает клавиатуру
fun hideKeyboard() {
    val imm: InputMethodManager = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken,0)
}//hideKeyboard

//Получаем время отправки погоды
fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}//String.asTime()

//Получаем дату отправки погоды
fun String.asDate(): String {
    val date = Date(this.toLong())
    val timeFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    return timeFormat.format(date)
}//String.asDate()





