package com.example.aaa.xxx.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aaa.xxx.weatherapp.R
import com.example.aaa.xxx.weatherapp.model.WeatherModel
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherAdapter(var weatherDay:ArrayList<WeatherModel>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){
    inner class WeatherViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_weather,parent,false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.itemView.apply{
                    tvDegree2.text=weatherDay[position].main.temp.toString()+ "°C"
                    Glide.with(this)
                            .load("https://openweathermap.org/img/wn/" + weatherDay[position].weather.get(0).icon + "@2x.png")
                            .into(ivDescripe)
                    tvDescription2.text = "Hava: "+weatherDay[position].weather[0].description.toUpperCase()
                    tvDate.text="Tarih:"+weatherDay[position].dt_txt.substring(0,10)
            tvHour.text="Saat: "+weatherDay[position].dt_txt.substring(10,16)
        }
    }

    fun RefreshDay(weatherDays: List<WeatherModel>){
        weatherDay.clear()
        weatherDay.addAll(weatherDays)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return weatherDay.size
    }
}