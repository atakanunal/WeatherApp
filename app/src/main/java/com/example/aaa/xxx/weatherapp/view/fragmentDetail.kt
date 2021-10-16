package com.example.aaa.xxx.weatherapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aaa.xxx.weatherapp.R
import com.example.aaa.xxx.weatherapp.adapter.WeatherAdapter
import com.example.aaa.xxx.weatherapp.viewmodel.fragmentdetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.*

class fragmentDetail : Fragment(R.layout.fragment_detail) {
    private lateinit var viewModel:fragmentdetailViewModel
    private var rvAdapter=WeatherAdapter(arrayListOf())




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel= ViewModelProviders.of(this).get(fragmentdetailViewModel::class.java)

        if(arguments!=null){
            val args=fragmentDetailArgs.fromBundle(requireArguments())
            val cityName=args.cityName
            viewModel.refreshData(cityName)
            SwipeRefresh2.setOnRefreshListener {
                tvError2.visibility=View.GONE
                rv.visibility=View.GONE
                progressBar2.visibility=View.GONE
                viewModel.refreshData(cityName)
                SwipeRefresh2.isRefreshing=false
            }
        }
        getLiveData()
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(context)
    }
    private fun getLiveData(){
        viewModel.day.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                rv.visibility=View.VISIBLE
                rvAdapter.RefreshDay(data.list)
            }
        })
    }
}