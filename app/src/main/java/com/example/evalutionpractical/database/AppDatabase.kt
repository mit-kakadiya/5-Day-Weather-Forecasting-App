package com.example.RoomDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.evalutionpractical.database.Dao.AllWeatherDetailsDao
import com.example.evalutionpractical.database.Dao.WeatherDetailsDao
import com.example.evalutionpractical.database.Entity.AllWeatherDetailsEntity

@Database(entities = [WeatherDetailsEntity::class, AllWeatherDetailsEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
     abstract fun weatherDetailsDao(): WeatherDetailsDao
     abstract fun allDetailsDao():AllWeatherDetailsDao
}