package com.example.evalutionpractical.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.RoomDataBase.WeatherDetailsEntity

@Dao
interface WeatherDetailsDao {
    @Query("SELECT * FROM weatherdetails")
    fun getWeatherDetails(): LiveData<List<WeatherDetailsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherDetails(weatherDetails: WeatherDetailsEntity)

    @Query("SELECT city_name FROM weatherdetails")
    fun cityName():String
}
