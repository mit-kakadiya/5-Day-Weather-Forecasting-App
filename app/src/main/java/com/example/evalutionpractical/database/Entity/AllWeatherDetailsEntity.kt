package com.example.evalutionpractical.database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AllWeatherDetails")
data class AllWeatherDetailsEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "city_name") val cityName: String?,
    @ColumnInfo(name = "weather_details") val weatherDetails: String?
)
