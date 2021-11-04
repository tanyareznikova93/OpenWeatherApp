package com.weather.openweatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weather.openweatherapp.R
import com.weather.openweatherapp.fragment.favcity.FavCityFragment.Companion.listFavCity
import com.weather.openweatherapp.model.weather.WeatherModel
import kotlinx.android.synthetic.main.fav_city_item_layout.view.*
import kotlinx.android.synthetic.main.fragment_change_city.view.*


class ChangeCityAdapter(
    //var itemList: MutableList<WeatherModel>,
    //private var listener: OnItemClickListener
) :RecyclerView.Adapter<ChangeCityAdapter.ViewHolder>() {

    companion object{
        const val VIEW_TYPE_WRITE_TO_DB = 1
        const val VIEW_TYPE_READ_FROM_DB = 0
    }

    private var listItem = mutableListOf<WeatherModel>()
    private var isExpanded: Boolean = false

    /*
    interface OnItemClickListener{
        fun OnItemClick(item:WeatherModel,position: Int)
    }//OnItemClickListener

     */

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            VIEW_TYPE_WRITE_TO_DB
        } else {
            VIEW_TYPE_READ_FROM_DB
        }
    }//getItemViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        return when(viewType){
            VIEW_TYPE_WRITE_TO_DB -> {
                ViewHolder.FavCityHolder2(
                    layoutInflater.inflate(R.layout.fav_city_item_layout,parent,false)
                )
            }
            else -> {
                ViewHolder.ChangeCityHolder(
                    layoutInflater.inflate(R.layout.recyclerview_changecity,parent,false)
                )
            }
        }

        /*
        holder.itemView.setOnClickListener {

            //showToast("Click")
            //restartActivity()

            //if(listFavCity[holder.adapterPosition].name)

            when(listFavCity[holder.adapterPosition].name) {
                listFavCity[holder.adapterPosition].name -> replaceFragment(ChangeCityFragment())
                //listFavCity[holder.adapterPosition].name -> showToast("$listFavCity")
            }

            /*
            when(listItem[holder.adapterPosition].name) {
                listItem[holder.adapterPosition].name -> replaceFragment(ChangeCityFragment())
            }

             */




        }


         */

    }//onCreateViewHolder

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //holder.itemFavCityTitle.text = listFavCity[position].name
        when(holder){
            is ViewHolder.FavCityHolder2 -> {
                holder.onBind(listFavCity[holder.adapterPosition], onFavCityClicked())
            }
            is ViewHolder.ChangeCityHolder -> {
                holder.onBind(listFavCity[holder.adapterPosition])
            }
        }

    }//onBindViewHolder

    override fun getItemCount(): Int = listFavCity.size

    fun updateListItems(item:WeatherModel){

        if(!listFavCity.contains(item)) {
            listFavCity.add(item)
            //listItem.addAll(listOf(item))
            notifyItemInserted(listFavCity.size)
        }

    }//updateListItems

    private fun onFavCityClicked() = object: View.OnClickListener {
        override fun onClick(p0: View?) {
            isExpanded = !isExpanded
            if(isExpanded){
                notifyItemRangeInserted(1,listFavCity.size)
                notifyItemChanged(0)
            } else {
                notifyItemRangeRemoved(1,listFavCity.size)
                notifyItemChanged(0)
            }
        }//onClick

    }//onFavCityClicked

    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        class FavCityHolder2(view: View) : ViewHolder(view) {

            val itemFavCityTitle:TextView = view.fav_city_item_title_tv

            fun onBind(itemFavCity: WeatherModel, onClickListener: View.OnClickListener){
                itemFavCityTitle.text = itemFavCity.name
                itemView.setOnClickListener {
                    onClickListener.onClick(it)
                }
            }

        }//FavCityHolder

        class ChangeCityHolder(view: View) : ViewHolder(view) {

            val itemFavCityTitle:TextView = view.tv_city_name_current_change_city

            fun onBind(itemChangeCity:WeatherModel){
                itemFavCityTitle.text = itemChangeCity.name
            }

        }//FavCityHolder

    }//ViewHolder

}//MainListAdapter