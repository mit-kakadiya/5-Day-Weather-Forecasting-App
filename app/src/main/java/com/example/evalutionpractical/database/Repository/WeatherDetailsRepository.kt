package com.example.evalutionpractical.database.Repository

import androidx.lifecycle.LiveData
import com.example.RoomDataBase.WeatherDetailsEntity
import com.example.evalutionpractical.database.Dao.WeatherDetailsDao
import javax.inject.Inject

class WeatherDetailsRepository @Inject constructor(private val weatherDetailsDao: WeatherDetailsDao) {
    fun getWeatherDetails():LiveData<List<WeatherDetailsEntity>>{
       return weatherDetailsDao.getWeatherDetails()
    }
    fun insertWeatherDetails(weatherDetails: WeatherDetailsEntity){
        weatherDetailsDao.insertWeatherDetails(weatherDetails)
    }
    fun getLastCityName():String{
        return weatherDetailsDao.cityName()
    }
}