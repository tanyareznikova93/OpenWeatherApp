package com.weather.openweatherapp.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.weather.openweatherapp.R
import com.weather.openweatherapp.database.SQLiteHelper
import kotlinx.android.synthetic.main.dialog_fav_city.view.*

class CustomDialogFragment: DialogFragment() {

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.dialog_fav_city,container,false)

        rootView.dialog_cancel_btn.setOnClickListener {
            sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
            sqLiteHelper.deleteCityById(id)
            dismiss() }

        rootView.dialog_submit_btn.setOnClickListener { dismiss() }

        return rootView

    }//onCreateView

}//CustomDialogFragment