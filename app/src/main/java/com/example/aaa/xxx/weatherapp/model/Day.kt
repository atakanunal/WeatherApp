package com.example.aaa.xxx.weatherapp.model


data class Day(
    val cnt: Int,
    val cod: String,
    val list: List<WeatherModel>,
    val message: Int
)