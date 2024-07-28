package com.example.evalutionpractical.database.Repository

import androidx.lifecycle.LiveData
import com.example.evalutionpractical.database.Dao.AllWeatherDetailsDao
import com.example.evalutionpractical.database.Entity.AllWeatherDetailsEntity
import javax.inject.Inject

class AllWeatherDetailsRepository @Inject constructor(private val allDetailsDao: AllWeatherDetailsDao) {

    fun getDetailsFromDatabase(): LiveData<List<AllWeatherDetailsEntity>> {
        return allDetailsDao.getWeatherDetails()
    }

     fun insertDetailsToDatabase(weatherDetailsEntity: AllWeatherDetailsEntity) {
        allDetailsDao.insertWeatherDetails(weatherDetailsEntity)
    }

    fun getWeatherByCityName(cityName: String): AllWeatherDetailsEntity?{
        return allDetailsDao.getWeatherByCityName(cityName)
    }
    fun getCityList():LiveData<List<String?>>{
        return allDetailsDao.getCityNames()
    }
    fun updateWeatherData(cityName: String,weatherDetails: String){
        allDetailsDao.updateWeatherDetails(cityName,weatherDetails)
    }
}