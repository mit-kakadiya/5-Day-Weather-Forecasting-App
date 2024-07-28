package com.example.evalutionpractical.ui.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RoomDataBase.WeatherDetailsEntity
import com.example.evalutionpractical.database.Repository.WeatherDetailsRepository
import com.example.evalutionpractical.Model.Resource
import com.example.evalutionpractical.Model.Temp
import com.example.evalutionpractical.Network.ApiConstants
import com.example.evalutionpractical.Network.NetworkHelper
import com.example.evalutionpractical.R
import com.example.evalutionpractical.database.Entity.AllWeatherDetailsEntity
import com.example.evalutionpractical.database.Repository.AllWeatherDetailsRepository
import com.example.evalutionpractical.ui.repository.WeatherDataRepository
import com.example.evalutionpractical.common.utills.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OneDayWeatherViewModel @Inject constructor(
    private val repository: WeatherDataRepository,
    private val weatherDetailsRepository: WeatherDetailsRepository,
    private val allWeatherDetailsRepository: AllWeatherDetailsRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    val onNextBtnClick = SingleLiveEvent<Void>()
    val cityName = MutableLiveData<String>()
    val curruntDayTemp = MutableLiveData<String>()
    val dayOneTemp = MutableLiveData<String>()
    val dayTwoTemp = MutableLiveData<String>()
    val dayThreeTemp = MutableLiveData<String>()
    val tempListVm = MutableLiveData<ArrayList<String>>()
    val windListVm = MutableLiveData<ArrayList<String>>()
    val weatherDataList = MutableLiveData<Resource<Temp>>()
    val lastCityName = MutableLiveData<String>()

    @Inject
    @ApplicationContext
    lateinit var context: Context


    fun clickOnNextButton() {
        onNextBtnClick.call()
    }

    fun getDataFromDataBase(): LiveData<List<WeatherDetailsEntity>> {
        return weatherDetailsRepository.getWeatherDetails()
    }

    fun insertDataIntoDataBase(weatherDetailsEntity: WeatherDetailsEntity) {
        viewModelScope.launch {
            weatherDetailsRepository.insertWeatherDetails(weatherDetailsEntity)
        }
    }

    fun getAllDataFromDataBase(): LiveData<List<AllWeatherDetailsEntity>> {
        return allWeatherDetailsRepository.getDetailsFromDatabase()
    }

    fun isCityExistInDatabase(cityName: String): Boolean {
        return allWeatherDetailsRepository.getWeatherByCityName(cityName)?.cityName?.isNotEmpty()
            ?: false
    }

    fun getDataByCity(cityName: String): AllWeatherDetailsEntity? {
        return allWeatherDetailsRepository.getWeatherByCityName(cityName)
    }

    fun insertAllDataToDataBase(allWeatherDetailsEntity: AllWeatherDetailsEntity) {
        viewModelScope.launch {
            allWeatherDetailsRepository.insertDetailsToDatabase(allWeatherDetailsEntity)
        }
    }

    fun getLastCityName(){
      lastCityName.value = weatherDetailsRepository.getLastCityName()
    }

    fun updateWeatherDetails(cityName: String,weatherDetails: String){
        allWeatherDetailsRepository.updateWeatherData(cityName,weatherDetails)
    }

    fun getData(apiKey: String, days: String, cityName: String, aqi: String, alerts: String) {
        val dataMap = HashMap<String, String>().apply {
            put(ApiConstants.KEY, apiKey)
            put(ApiConstants.CITY_NAME, cityName)
            put(ApiConstants.DAYS, days)
            put(ApiConstants.AQI, aqi)
            put(ApiConstants.ALERTS, alerts)
        }
        viewModelScope.launch {
            if (networkHelper.isInternetConnected()) {
                repository.getWeatherData(dataMap).collect {
                    weatherDataList.value = it
                }
            } else {
                Toast.makeText(context,
                    context.getString(R.string.network_is_not_available), Toast.LENGTH_SHORT).show()
            }
        }
    }
}