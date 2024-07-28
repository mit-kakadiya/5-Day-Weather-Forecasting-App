package com.example.evalutionpractical.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class FiveDayWeatherViewModel:ViewModel(){
    val tempListVm = MutableLiveData<ArrayList<String>>()
    val dateListVm = MutableLiveData<ArrayList<String>>()
}