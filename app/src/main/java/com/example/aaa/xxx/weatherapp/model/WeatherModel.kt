package com.example.aaa.xxx.weatherapp.model



data class WeatherModel(
        val coord: Coord,
        var id: Int=0,
        val main: Main,
        val name: String,
        val sys: Sys,
        val dt_txt: String,
        val weather: List<Weather>,
        val wind: Wind
)