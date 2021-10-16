package com.example.aaa.xxx.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aaa.xxx.weatherapp.model.Day
import com.example.aaa.xxx.weatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class fragmentdetailViewModel() : ViewModel() {
    private val weatherAPIService=WeatherAPIService()
    private val disposable=CompositeDisposable()
    private val weatherMsg=MutableLiveData<Boolean>()
    private val weatherLoad=MutableLiveData<Boolean>()
    val day=MutableLiveData<Day>()

    fun refreshData(cityName:String){
        getDataFromAPI(cityName)
    }

    fun getDataFromAPI(cityName: String){
        disposable.add(
                weatherAPIService.getDataServiceDay(cityName)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object:DisposableSingleObserver<Day>(){
                            override fun onSuccess(t: Day){
                                day.value=t
                                weatherMsg.value=false
                                weatherLoad.value=false
                            }
                            override fun onError(e:Throwable){
                                weatherMsg.value=true
                                weatherLoad.value=false
                            }
                        })
        )
        weatherAPIService.getDataServiceDay(cityName)
    }
}