package com.example.evalutionpractical.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.evalutionpractical.database.Entity.AllWeatherDetailsEntity

@Dao
interface AllWeatherDetailsDao {
    @Query("SELECT * FROM allweatherdetails")
    fun getWeatherDetails(): LiveData<List<AllWeatherDetailsEntity>>

    @Query("SELECT * FROM allweatherdetails WHERE city_name = :cityName")
    fun getWeatherByCityName(cityName: String): AllWeatherDetailsEntity?

    @Insert
    fun insertWeatherDetails(weatherDetails: AllWeatherDetailsEntity)

    @Query("SELECT city_name FROM AllWeatherDetails")
    fun getCityNames(): LiveData<List<String?>>

    @Query("UPDATE allweatherdetails SET weather_details = :weatherDetails WHERE city_name = :cityName")
    fun updateWeatherDetails(cityName: String, weatherDetails: String)
}
