package com.weather.openweatherapp.fragment.favcity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.weather.openweatherapp.R
import com.weather.openweatherapp.adapter.FavCityAdapter
import com.weather.openweatherapp.database.CityNameModel
import com.weather.openweatherapp.database.SQLiteHelper
import com.weather.openweatherapp.databinding.FragmentFavCityBinding
import com.weather.openweatherapp.fragment.changecity.ChangeCityFragment
import com.weather.openweatherapp.model.weather.WeatherModel
import com.weather.openweatherapp.utils.*
//import com.weather.openweatherapp.utils.replaceActivity
import com.weather.openweatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fav_city_item_layout.*
import kotlinx.android.synthetic.main.fragment_fav_city.*
import java.util.ArrayList

const val TAG = "FavCityFragment"

class FavCityFragment : Fragment(R.layout.fragment_fav_city) {

    private lateinit var viewModel: MainViewModel

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private lateinit var binding: FragmentFavCityBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FavCityAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private var listItem = mutableListOf<WeatherModel>()
    private lateinit var listItemFavCity: WeatherModel
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var cn: CityNameModel
    var cnList = ArrayList<CityNameModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }//onCreate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavCityBinding.bind(view)

        GET = APP_ACTIVITY.getSharedPreferences(APP_ACTIVITY.packageName, AppCompatActivity.MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        val cName = GET.getString("cityName", "moscow")
        edt_city_name_fav_city.setText(cName)
        //fav_city_item_title_tv.setText(cName)

        viewModel.refreshDataForFavCity(cName!!)
        //viewModel.refreshData(cName!!)
        //viewModel.refreshForecastData(cName!!)
        //viewModel.refreshForecastData2(cName!!)

        initRecyclerView()
        sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
        getLiveDataFavCity()

        getCity()
        //cleanEditText()
        cn = CityNameModel()

        swipe_refresh_fav_city.setOnRefreshListener {
            city_recycler_view.visibility = View.GONE
            tv_error_fav_city.visibility = View.GONE
            pb_loading_fav_city.visibility = View.GONE

            val cityName = GET.getString("cityName", cName)
            edt_city_name_fav_city.setText(cityName)
            //fav_city_item_title_tv.text.toString()
            viewModel.refreshDataForFavCity(cityName!!)
            swipe_refresh_fav_city.isRefreshing = false
        }

        img_search_fav_city.setOnClickListener {

            val cityName = edt_city_name_fav_city.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()

            initRecyclerView()
            viewModel.refreshDataForFavCity(cityName)
            getLiveDataFavCity()

            cn.cityname = cityName

            addCity(cityName)
            cleanEditText()
            getCity()

            Log.i(com.weather.openweatherapp.view.TAG, "onCreate: " + cityName)
            hideKeyboard()

        }

    }//onViewCreated

    private fun addCity(cityName:String){

        sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
        val cn = CityNameModel(cityname = cityName)
        val status = sqLiteHelper.insertCityName(cn)
        if(status > -1){
            showToast("Добавлен город - $cityName")
            cleanEditText()
        }

    }//addCity

    fun updateCities(cn:CityNameModel){

        sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
        val cnm = CityNameModel(id = cn.id,cityname = cn.cityname)
        val status = sqLiteHelper.updateCity(cnm)
        if(status > -1){
            showToast("Обновлен город - ${cnm.cityname}")
            cleanEditText()
            //getCity()
        }

    }//updateCities

    fun deleteCityNameByID(id:Int){
        val dialog = CustomDialogFragment()
        fragmentManager?.let { dialog.show(it, "customDialog") }
    }//deleteCityNameByID

    fun cleanEditText(){
        edt_city_name_fav_city.setText("")
    }


    private fun getCity(){
        val cNameList = sqLiteHelper.getAllCityName()
        showToast("Выведен на экран : ${cNameList.size}")
        //mAdapter.updateCities(cNameList)
        mAdapter.updateCities(cNameList)
    }

    private fun initRecyclerView() {
        mRecyclerView = city_recycler_view
        mAdapter = FavCityAdapter()
        mRecyclerView.adapter = mAdapter
        mLayoutManager = LinearLayoutManager(APP_ACTIVITY)
    }//initRecyclerView

    override fun onResume() {
        super.onResume()

        back_iv_from_fav_city.setOnClickListener {
            restartActivity()
        }
    }//onResume

    fun getLiveDataFavCity() {

        //viewLifecycleOwner
        viewModel.weather_data_fav_city.observe(APP_ACTIVITY, Observer { data ->
            data?.let {

                city_recycler_view.visibility = View.VISIBLE

                //getCity()
                //mAdapter.updateListItems(data)
                //getCity()
                //mAdapter.updateCities(cityNameList)

            }
        })

        viewModel.weather_error_fav_city.observe(APP_ACTIVITY, Observer { error ->
            error?.let {
                if (error) {
                    tv_error_fav_city.visibility = View.VISIBLE
                    pb_loading_fav_city.visibility = View.GONE
                    city_recycler_view.visibility = View.GONE
                } else {
                    tv_error_fav_city.visibility = View.GONE
                }
            }
        })

        viewModel.weather_loading_fav_city.observe(APP_ACTIVITY, Observer { loading ->
            loading?.let {
                if (loading) {
                    pb_loading_fav_city.visibility = View.VISIBLE
                    tv_error_fav_city.visibility = View.GONE
                    city_recycler_view.visibility = View.GONE
                } else {
                    pb_loading_fav_city.visibility = View.GONE
                }
            }
        })

    }//getLiveDataFavCity


    companion object {
        val listFavCity = mutableListOf<WeatherModel>()
        var cityNameList = mutableListOf<CityNameModel>()
    }


}//FavCityFragment