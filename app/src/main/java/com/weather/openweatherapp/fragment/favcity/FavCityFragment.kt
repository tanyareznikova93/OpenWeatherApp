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
import com.weather.openweatherapp.R
import com.weather.openweatherapp.adapter.FavCityAdapter
import com.weather.openweatherapp.database.CityNameModel
import com.weather.openweatherapp.database.room.CityEntity
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

class FavCityFragment : Fragment(R.layout.fragment_fav_city) //, FavCityAdapter.OnItemClickListener
{

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
    //private var cnList: ArrayList<CityNameModel> = ArrayList()
    var cnList = ArrayList<CityNameModel>()
    //private var cityList = CityNameModel
    private lateinit var allCity: MutableLiveData<List<CityEntity>>
    private lateinit var changeCityFragment: ChangeCityFragment

    /*
    private val itemClickListener: (data: WeatherModel) -> Unit = { dataItem->
        showToast("Item Click on: $dataItem")
        //Toast.makeText(requireContext(),"Item Click on: $dataItem",Toast.LENGTH_LONG).show()
    }//itemClickListener

     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }//onCreate

    /*
    override fun onAttach(context: Context) {
        super.onAttach(context)
        //очищаем лист перед запуском
        //listFavCity.clear()
    }

     */

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
        //cn?.cityname = cName

        initRecyclerView()
        sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
        getLiveDataFavCity()

        getCity()
        //cleanEditText()
        cn = CityNameModel()
        //cn.cityname = cName
        //cnList[0].cityname = cName

        /*
        swipe_refresh_layout.setOnRefreshListener {
            ll_data.visibility = View.GONE
            hourly_recycler_view.visibility = View.GONE
            data_daily_ll.visibility = View.GONE
            data_daily_ll2.visibility = View.GONE
            data_daily_ll3.visibility = View.GONE
            data_daily_ll4.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.GONE

            val cityName = GET.getString("cityName", cName)
            edt_city_name.setText(cityName)
            viewModel.refreshData(cityName!!)
            viewModel.refreshForecastData(cityName!!)
            viewModel.refreshForecastData2(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }

         */


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

            //viewModel.refreshDataForFavCity(cityName)
            //viewModel.refreshData(cityName)
            //viewModel.refreshForecastData(cityName)
            //viewModel.refreshForecastData2(cityName)
            initRecyclerView()
            viewModel.refreshDataForFavCity(cityName)
            getLiveDataFavCity()

            //updateCities(cn)
            //insertOrReplaceCities(cn)

            cn.cityname = cityName
            //insertOrReplaceCities(cn)

            /*
            if(cityName == cn.cityname){
                //updateCities(cn)
                addCity(cityName)
                //replaceCity(cityName)
                cleanEditText()
                getCity()
            } else {
                //addCity(cityName)
                //updateCities(cn)
                replaceCity(cityName)
                cleanEditText()
                getCity()
            }

             */

            //insertCity(cn)
            //updateCities(cn)
            //cleanEditText()
            //getCity()

            addCity(cityName)
            cleanEditText()
            getCity()

            Log.i(com.weather.openweatherapp.view.TAG, "onCreate: " + cityName)
            //getLiveDataFavCity()



            //APP_ACTIVITY.getLiveData()
            //APP_ACTIVITY.getLiveDataDaily()
            //APP_ACTIVITY.getLiveDataHourly()

            /*
            sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
            val cn = CityNameModel(cityname = cityName)
            val status = sqLiteHelper.insertCityName(cn)
            if(status > -1){
                showToast("Добавлен город - $cityName")
                cleanEditText()
            }

             */

            //cn.cityname = cityName
            //cn.cityname = cityName
            //cn.cityname = cName
            //replaceFragment(FavCityFragment())

            /*
            if(cityName == cn.cityname){
                //sqLiteHelper.insertCityName(cn)
                //sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
                showToast("Обновлен город - $cityName")
                //val cnm = CityNameModel(id = cn.id,cityname = cn.cityname)
                //val status = sqLiteHelper.updateCity(cnm)
                updateCities(cn)
                //viewModel.refreshDataForFavCity(cityName)
                //getLiveDataFavCity()
                cleanEditText()
                getCity()
            } else {
                //updateCities(cn)
                showToast("Добавлен город - $cityName")
                //val cityN = CityNameModel(cityname = cityName)
                //val status = sqLiteHelper.insertCityName(cityN)
                addCity(cityName)
                //viewModel.refreshDataForFavCity(cityName)
                //getLiveDataFavCity()
                cleanEditText()
                getCity()
            }

             */

            //viewModel.refreshData(cityName)
            //viewModel.refreshForecastData(cityName)
            //viewModel.refreshForecastData2(cityName)
            //viewModel.refreshDataForFavCity(cityName)


            //cleanEditText()

            //addCity()
            //getCity()

            //replaceFragment(ChangeCityFragment())

            //edt_city_name_fav_city.clearComposingText()

        }
        /*
        val item = object: SwipeToDelete(APP_ACTIVITY,0, ItemTouchHelper.LEFT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mAdapter.deleteCities(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(city_recycler_view)

         */

        /*
        add_fav_city_iv.setOnClickListener {
            if(cName.isEmpty()){
                showToast("Введите название города")
            } else {
                restartActivity()
            }
        }

         */

    }//onViewCreated

    fun addCity(cityName:String){

        //val cityName = edt_city_name_fav_city.text.toString()
        //SET.putString("cityName", cityName)
        //SET.apply()

        //viewModel.refreshDataForFavCity(cityName)

        //initRecyclerView()
        //getLiveDataFavCity()

        //Log.i(com.weather.openweatherapp.view.TAG, "onCreate: " + cityName)

        sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
        val cn = CityNameModel(cityname = cityName)
        val status = sqLiteHelper.insertCityName(cn)
        if(status > -1){
            showToast("Добавлен город - $cityName")
            cleanEditText()
        }


    }//addCity

    /*
    fun insertOrReplaceCities(cn:CityNameModel){

        sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
        val cnm = CityNameModel(id = cn.id,cityname = cn.cityname)
        val status = sqLiteHelper.insertOrReplaceCity(cnm)
        if(status > -1){
            //showToast("Добавлен город - $cityName")
            cleanEditText()
            //getCity()
        }


    }//updateCities

     */

    /*
    fun replaceCity(cityName:String){

        //val cityName = edt_city_name_fav_city.text.toString()
        //SET.putString("cityName", cityName)
        //SET.apply()

        //viewModel.refreshDataForFavCity(cityName)

        //initRecyclerView()
        //getLiveDataFavCity()

        //Log.i(com.weather.openweatherapp.view.TAG, "onCreate: " + cityName)

        sqLiteHelper = SQLiteHelper(APP_ACTIVITY)
        val cnm = CityNameModel(cityname = cityName)
        val status = sqLiteHelper.replaceCityName(cnm)
        if(status > -1){
            showToast("Обновлен город - $cityName")
            cleanEditText()
        }


    }//replaceCity

     */

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

    /*
    fun deleteCities(id:Int){
        if(id == null) return

        val builder = AlertDialog.Builder(APP_ACTIVITY)
        builder.setMessage("Удалить город из избранного?")
        builder.setCancelable(true)

        builder.setPositiveButton("Да"){dialog, _ ->
            sqLiteHelper.deleteCityById(id)
            getCity()
            dialog.dismiss()
        }
        builder.setNegativeButton("Нет"){dialog, _ ->
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }//deleteCities

     */


    fun cleanEditText(){
        edt_city_name_fav_city.setText("")
    }


    fun getCity(){
        val cNameList = sqLiteHelper.getAllCityName()
        showToast("Выведен на экран : ${cNameList.size}")
        //mAdapter.updateCities(cNameList)
        mAdapter.updateCities(cNameList)
    }


    /*
    override fun OnItemClick(item: WeatherModel,position: Int) {

        //val intent = Intent(APP_ACTIVITY,MainActivity::class.java)
        //intent.putExtra("CITYNAME",item.name)

        //val fragmentManager = APP_ACTIVITY.supportFragmentManager.beginTransaction()
        //fragmentManager.add(item.name)
        showToast(item.name)

        //tv_city_name_current_change_city.setText(item.name)
        //tv_degree_current_change_city.setText(item.main.temp.toString())
        //tv_weather_description_current_change_city.setText(item.weather.get(position).description)
        //tv_humidity_current_change_city.setText(item.main.humidity)
        //tv_wind_speed_current_change_city.setText(item.wind.speed.toString())
        //tv_city_name_current_change_city.text = item.name

        replaceFragment(ChangeCityFragment())

        //viewModel.refreshData(item.name)
        //viewModel.refreshDataForFavCity(item.name)
        //viewModel.refreshForecastData(item.name)
        //viewModel.refreshForecastData2(item.name)
        //restartActivity()
        //APP_ACTIVITY.getLiveData()
        //getLiveDataFavCity()
        //APP_ACTIVITY.getLiveDataHourly()
        //APP_ACTIVITY.getLiveDataDaily()

        //restartActivity()

        //val clickedItem = listFavCity[position]
        //clickedItem.name.toString()
        //mAdapter.notifyItemChanged(position)
        //restartActivity()
        //mAdapter.updateListItems(clickedItem)
    }//OnItemClick

     */

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

    /*
    init {
        allCity = MutableLiveData()
    }

    fun getAllCityObservers(): MutableLiveData<List<CityEntity>> {
        return allCity
    }
    fun getAllCity() {
        val cityDao = RoomAppDb.getAppDatabase((APP_ACTIVITY))?.cityDao()
        val list = cityDao?.getAllCity()
    }

     */


    fun getLiveDataFavCity() {

        //viewLifecycleOwner
        viewModel.weather_data_fav_city.observe(APP_ACTIVITY, Observer { data ->
            data?.let {

                city_recycler_view.visibility = View.VISIBLE

                //getCity()
                //mAdapter.updateListItems(data)
                //getCity()
                //mAdapter.updateCities(cityNameList)

                //mAdapter.updateListItems(data)
                //mAdapter.updateListItems(data)
                //mAdapter.updateListItems(data)
                //mAdapter.updateListItems(data)
                //mAdapter.updateListItems(data)


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