package com.example.evalutionpractical.ui.repository

import com.example.evalutionpractical.Model.City
import com.example.evalutionpractical.Model.Resource
import com.example.evalutionpractical.Network.ApiService
import com.example.evalutionpractical.Network.BaseApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CityDataRepository @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) {
    fun getCityData(dataMap: HashMap<String, String>): Flow<Resource<City>> {
        return flow {
            emit(Resource.loading())
            emit(BaseApiResponse.safeApiCall { apiService.getCityLIst(dataMap) })
        }.flowOn(dispatcher)
    }
}