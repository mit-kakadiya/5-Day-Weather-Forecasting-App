package com.example.evalutionpractical.Network

import com.example.evalutionpractical.Model.Resource
import retrofit2.Response

class BaseApiResponse {
    companion object{
       suspend fun <T> safeApiCall(apiCall: suspend() -> Response<T>):Resource<T>{
           try {
               val response = apiCall()
               if (response.isSuccessful){
                   val body = response.body()
                   body?.let {
                       return Resource.success(body)
                   } ?: return Resource.error("Something went wrong")
               }
               else{
                   return Resource.error("${ response.code() } ${response.message()}")
               }
           }
           catch (e:Exception){
               return Resource.error(e.toString())
           }
        }
    }
}