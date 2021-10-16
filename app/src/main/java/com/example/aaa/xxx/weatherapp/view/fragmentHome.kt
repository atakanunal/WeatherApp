package com.example.aaa.xxx.weatherapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.aaa.xxx.weatherapp.R
import com.example.aaa.xxx.weatherapp.viewmodel.fragmenthomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class fragmentHome : Fragment(R.layout.fragment_home) {
    private lateinit var viewModel:fragmenthomeViewModel

    private lateinit var GET:SharedPreferences
    private lateinit var SET:SharedPreferences.Editor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel= ViewModelProviders.of(this).get(fragmenthomeViewModel::class.java)
        GET= activity?.getSharedPreferences("pref",Context.MODE_PRIVATE)!!
        SET=GET.edit()
        var cName=GET.getString("cityName","istanbul")?.toLowerCase()
        etCityname.setText(cName)
        viewModel.refreshData(cName!!)
        getLiveData()


        fab.setOnClickListener(){
            val action=fragmentHomeDirections.actionFragmentHomeToFragmentDetail(cName)
            view.findNavController().navigate(action)
        }

        swiperefresh.setOnRefreshListener {
            llData.visibility=View.GONE
            tvError.visibility=View.GONE
            progressBar.visibility=View.GONE

            var cityName=GET.getString("cityName",cName)
            etCityname.setText(cityName)
            viewModel.refreshData(cityName!!)
            swiperefresh.isRefreshing=false

        }
        ivSearch.setOnClickListener{
            val cityName=etCityname.text.toString()
            SET.putString("cityName",cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            getLiveData()
        }
    }

    private fun getLiveData() {
        viewModel.weatherData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                llData.visibility = View.VISIBLE

                tvCountryCode.text = "Şehir Kodu: " + data.sys.country.toString()
                tvCityName.text = "Şehir İsmi: " + data.name.toString()

                Glide.with(this)
                        .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                        .into(ivWeather)
                tvDegree.text = data.main.temp.toString() + "°C"

                tvHumidity.text = "Nem: " + data.main.humidity.toString() + "%"
                tvWindSpeed.text = "Rüzgar Hızı: " + data.wind.speed.toString()
                tvLatitude.text = "Eylem: " + data.coord.lat.toString()
                tvLongtitude.text = "Boylam: " + data.coord.lon.toString()
                tvDescription.text = "Hava: " + data.weather[0].description.capitalize()
            }
        })

        viewModel.weatherErrorMsg.observe(viewLifecycleOwner,Observer{error ->
            error?.let {
                if(error){
                    tvError.visibility=View.VISIBLE
                    progressBar.visibility=View.GONE
                    llData.visibility=View.GONE
                }else{
                    tvError.visibility=View.GONE
                }
            }
        })

        viewModel.weatherLoad.observe(viewLifecycleOwner,Observer{loading->
            loading?.let {
                if(loading){
                    progressBar.visibility=View.VISIBLE
                    tvError.visibility=View.GONE
                    llData.visibility=View.GONE
                }else{
                    progressBar.visibility=View.GONE
                }
            }
        })
    }
}