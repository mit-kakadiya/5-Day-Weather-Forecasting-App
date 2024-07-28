package com.example.evalutionpractical.Network

import com.example.evalutionpractical.Model.City
import com.example.evalutionpractical.Model.Temp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET(ApiConstants.END_POINT_FOR_FORECAST)
   suspend fun getWeatherData(
        @QueryMap hashMap: HashMap<String, String>
    ): Response<Temp>

   @GET(ApiConstants.END_POINT_FOR_SEARCH)
   suspend fun getCityLIst(@QueryMap hashMap: HashMap<String, String>):Response<City>
}