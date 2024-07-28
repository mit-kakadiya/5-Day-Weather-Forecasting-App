package com.example.RoomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WeatherDetails")
data class WeatherDetailsEntity(
    @PrimaryKey val uid: Int? = 1,
    @ColumnInfo(name = "city_name") val cityName: String?,
    @ColumnInfo(name = "weather_details") val weatherDetails: String?
)

