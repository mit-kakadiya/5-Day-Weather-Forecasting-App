package com.example.evalutionpractical.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evalutionpractical.Model.City
import com.example.evalutionpractical.Model.Resource
import com.example.evalutionpractical.Network.ApiConstants
import com.example.evalutionpractical.ui.repository.CityDataRepository
import com.example.evalutionpractical.common.utills.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(private val repository: CityDataRepository) :
    ViewModel() {
    val closeButtonClick = SingleLiveEvent<Void>()
    val searchButtonClick = SingleLiveEvent<Void>()
    val cityList = MutableLiveData<Resource<City>>()
    val cityName = MutableLiveData<String>()
    fun onCloseButtonClick() {
        closeButtonClick.call()
    }

    fun onSearchButtonClick() {
        searchButtonClick.call()
    }

    fun getCityLIst(apiKey:String,cityName:String) {
        viewModelScope.launch {
            val dataMap = HashMap<String, String>().apply {
                put(ApiConstants.KEY,apiKey)
                put(ApiConstants.CITY_NAME,cityName)
            }
            repository.getCityData(dataMap).collect {
                cityList.value = it
            }
        }
    }
}