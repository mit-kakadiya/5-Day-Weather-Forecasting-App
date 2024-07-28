package com.example.evalutionpractical.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.evalutionpractical.database.Repository.AllWeatherDetailsRepository
import com.example.evalutionpractical.common.utills.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfflineStoredDataViewModel @Inject constructor(private val allWeatherDetailsRepository: AllWeatherDetailsRepository):
    ViewModel() {
    val closeButtonClick = SingleLiveEvent<Void>()
    val cityName = MutableLiveData<String>()

    fun onCloseButtonClick() {
        closeButtonClick.call()
    }

    fun getCityLIst(): LiveData<List<String?>> {
            return allWeatherDetailsRepository.getCityList()
    }
}