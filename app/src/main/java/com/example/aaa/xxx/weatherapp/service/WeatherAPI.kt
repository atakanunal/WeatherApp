package com.example.aaa.xxx.weatherapp.service

import com.example.aaa.xxx.weatherapp.model.WeatherModel
import com.example.aaa.xxx.weatherapp.model.Day
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI {
    @GET("data/2.5/weather?&mode=json&units=metric&lang=tr&appid=a608bacf59533c7850f19434c47a9204")
    fun getData(
            @Query("q") cityName:String
    ):Single<WeatherModel>
    @GET("data/2.5/forecast?&units=metric&lang=tr&appid=a608bacf59533c7850f19434c47a9204")
    fun getDay(@Query("q") cityName: String): Single<Day>

}