package com.example.evalutionpractical.Model
import android.os.Parcelable

import kotlinx.parcelize.Parcelize


class City : ArrayList<CityItem>()

@Parcelize
data class CityItem(
    val country: String,
    val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String
) : Parcelable