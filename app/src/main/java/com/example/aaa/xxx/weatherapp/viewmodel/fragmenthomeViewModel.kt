package com.example.aaa.xxx.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aaa.xxx.weatherapp.model.WeatherModel
import com.example.aaa.xxx.weatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class fragmenthomeViewModel() : ViewModel() {
    private val weatherAPIService=WeatherAPIService()
    private val disposable=CompositeDisposable()

    val weatherData=MutableLiveData<WeatherModel>()
    val weatherErrorMsg=MutableLiveData<Boolean>()
    val weatherLoad=MutableLiveData<Boolean>()

    fun refreshData(cityName: String){
        getDataFromAPI(cityName)
    }
    fun getDataFromAPI(cityName:String){
        weatherLoad.value=true
        disposable.add(
            weatherAPIService.getDataService(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                        weatherData.value=t
                        weatherErrorMsg.value=false
                        weatherLoad.value=false
                    }

                    override fun onError(e: Throwable) {
                        weatherErrorMsg.value=true
                        weatherLoad.value=false
                        e.printStackTrace()
                    }
                })
        )
        weatherAPIService.getDataService(cityName)
    }
}