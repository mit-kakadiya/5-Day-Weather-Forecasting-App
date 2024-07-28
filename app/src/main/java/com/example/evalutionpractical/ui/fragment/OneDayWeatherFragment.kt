package com.example.evalutionpractical.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.RoomDataBase.WeatherDetailsEntity
import com.example.evalutionpractical.Model.Resource
import com.example.evalutionpractical.Model.Status
import com.example.evalutionpractical.Model.Temp
import com.example.evalutionpractical.Network.NetworkHelper
import com.example.evalutionpractical.R
import com.example.evalutionpractical.common.constants.Constants
import com.example.evalutionpractical.common.extension.createAndShowBottomSheetFragmentWithListener
import com.example.evalutionpractical.common.extension.fromJsonObjectToJsonString
import com.example.evalutionpractical.common.extension.fromJsonStringToJsonObject
import com.example.evalutionpractical.common.extension.hideProgressDialog
import com.example.evalutionpractical.common.extension.showProgressDialog
import com.example.evalutionpractical.database.Entity.AllWeatherDetailsEntity
import com.example.evalutionpractical.databinding.FragmentOneDayWeatherBinding
import com.example.evalutionpractical.listeners.CityListListener
import com.example.evalutionpractical.ui.activity.WeatherActivity
import com.example.evalutionpractical.view.dialog.CitySearchForWeatherDialog
import com.example.evalutionpractical.ui.viewModel.OneDayWeatherViewModel
import com.example.evalutionpractical.view.dialog.OfflineStoredCityWeatherDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class OneDayWeatherFragment : BaseFragment<FragmentOneDayWeatherBinding>(), CityListListener {
    private val mOneDayWeatherViewModel: OneDayWeatherViewModel by viewModels()
    private var mTempList: ArrayList<String> = ArrayList()
    private var mTempListInt: ArrayList<Int> = ArrayList()
    private var mWindSpeedList: ArrayList<String> = ArrayList()
    private var mCityName: String = "Surat"
    private var internetCheck: Boolean? = null
    private var newCity: String? = null

    @Inject
    lateinit var networkHelper: NetworkHelper

    override fun inflateLayout(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOneDayWeatherBinding {
        return FragmentOneDayWeatherBinding.inflate(layoutInflater)
    }


    override fun initView() {
        binding.oneDayWeatherVm = mOneDayWeatherViewModel
        binding.tvDay3.text = getDay()
        setUpToolBar()
        setUpSeekbarTouchFalse()
    }



    @SuppressLint("ClickableViewAccessibility")
    fun setUpSeekbarTouchFalse(){
        binding.cnDay1SeekBar.setOnTouchListener { _: View?, _: MotionEvent? -> true }
        binding.cnDay3SeekBar.setOnTouchListener { _: View?, _: MotionEvent? -> true }
        binding.cnDay2SeekBar.setOnTouchListener { _: View?, _: MotionEvent? -> true }
    }

    override fun setUpClickEvents() {
        mOneDayWeatherViewModel.apply {
            onNextBtnClick.observe(viewLifecycleOwner) {
                handle5DayForecastButtonClick()
            }
        }
    }

    override fun onCitySelected(city: String) {
        newCity = city
        mCityName = city
        internetCheck = networkHelper.isInternetConnected()
        refreshData()
        checkNetworkConnection()
    }

    override fun onObservers() {
        getLastCityName()
        Handler(Looper.getMainLooper()).postDelayed({ refreshData() }, 50)
        checkNetworkConnection()
    }

    private fun getLastCityName() {
        mOneDayWeatherViewModel.apply {
            getLastCityName()
            lastCityName.observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty()) {
                    mCityName = it
                }
            }
        }
    }

    private fun handle5DayForecastButtonClick() {
        val bundle = Bundle().apply {
            if (mTempList.isNotEmpty()) {
                putStringArrayList(
                    Constants.TEMP_LIST,
                    mTempList
                )
                putStringArrayList(
                    Constants.WIND_SPEED_LIST,
                    mWindSpeedList
                )
            }
            else{
                mTempList.addAll(listOf("22","22.5","28. 7","24","29"))
                mTempListInt.addAll(listOf(22,22,28,24,29))
            }
        }
        findNavController().navigate(
            R.id.action_oneDayWeatherFragment_to_fiveDayWeatherFragment,
            bundle
        )
    }

    private fun checkNetworkConnection() {
        internetCheck = networkHelper.isInternetConnected()
        if (internetCheck == true) {
            mOneDayWeatherViewModel.weatherDataList.observe(viewLifecycleOwner) {
                handleWeatherResponse(it)
            }
        } else {
            getDataFromDataBase()
        }
    }

    private fun getDay(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 2) // Add 2 days
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault()) // Format as full day name
        return dateFormat.format(calendar.time)
    }

    private fun setUpToolBar() {
        (activity as WeatherActivity).apply {
            this.getBindingFromActivity().apply {
                layoutToolBar.apply {
                    ivPlus.visibility = View.VISIBLE
                    ivMenu.visibility = View.VISIBLE
                    ivBack.visibility = View.INVISIBLE
                    ivPlus.setOnClickListener {
                        handleOnPlusButtonClick()
                    }
                    ivMenu.setOnClickListener {
                        handleOnMenuButtonClick()
                    }
                }
            }
        }
    }

    private fun insertAllData(fromJsonObjectToJsonString: String) {
        mOneDayWeatherViewModel.insertDataIntoDataBase(
            WeatherDetailsEntity(
                cityName = mCityName,
                weatherDetails = fromJsonObjectToJsonString
            )
        )
        if (!mOneDayWeatherViewModel.isCityExistInDatabase(mCityName)) {
            mOneDayWeatherViewModel.insertAllDataToDataBase(
                AllWeatherDetailsEntity(
                    cityName = mCityName,
                    weatherDetails = fromJsonObjectToJsonString
                )
            )
        } else {
            mOneDayWeatherViewModel.updateWeatherDetails(mCityName, fromJsonObjectToJsonString)
        }
    }

    private fun handleOnPlusButtonClick() {
        requireActivity().supportFragmentManager.createAndShowBottomSheetFragmentWithListener<CitySearchForWeatherDialog> {
            it.setUpListener(this)
        }
    }

    private fun handleOnMenuButtonClick() {
        requireActivity().supportFragmentManager.createAndShowBottomSheetFragmentWithListener<OfflineStoredCityWeatherDialog> {
            it.setUpListener(this)
        }
    }

    private fun clearAllList() {
        mTempList.clear()
        mWindSpeedList.clear()
        mTempListInt.clear()
    }

    private fun setWeatherData(
        tempList: ArrayList<String>,
        tempListInt: ArrayList<Int>,
        city: String
    ) {
        binding.apply {
            if (tempList.isNotEmpty() && tempListInt.isNotEmpty()){
                Log.d("XXX","$tempList $tempListInt")
                tvDay1temp2.text = tempList.getOrNull(0) ?: "N/A"
                tvTemp.text = tempList.getOrNull(0) ?: "N/A"
                tvDay2temp2.text = tempList.getOrNull(1) ?: "N/A"
                tvDay3temp2.text = tempList.getOrNull(2) ?: "N/A"
                tvCity.text = city
                cnDay1SeekBar.progress = tempListInt.getOrNull(0) ?: 0
                cnDay2SeekBar.progress = tempListInt.getOrNull(1) ?: 0
                cnDay3SeekBar.progress = tempListInt.getOrNull(2) ?: 0
            }
        }
    }

    private fun handleAndSetData(temp: Temp) {
        if (temp.forecast.forecastday.isNotEmpty()) {
            for (i in temp.forecast.forecastday.indices) {
                mTempList.add(temp.forecast.forecastday[i].day.avgtemp_c.toString())
                mTempListInt.add(temp.forecast.forecastday[i].day.avgtemp_c.toInt())
                mWindSpeedList.add(temp.forecast.forecastday[i].day.maxwind_kph.toString())
            }
            mOneDayWeatherViewModel.tempListVm.value = mTempList
            mOneDayWeatherViewModel.windListVm.value = mWindSpeedList
            setWeatherData(mTempList, mTempListInt, mCityName)
        }
        else{
            Toast.makeText(
                requireContext(),
                getString(R.string.offline_data_is_not_available),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun getDataFromDataBase() {
        if (newCity == null) {
            mOneDayWeatherViewModel.getDataFromDataBase().observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.btnNext.isEnabled = true
                    clearAllList()
                    val temp = fromJsonStringToJsonObject(it[0].weatherDetails!!)
                    handleAndSetData(temp)
                } else {
                    binding.btnNext.isEnabled = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.offline_data_is_not_available),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            binding.btnNext.isEnabled = true
            val currentWeather: AllWeatherDetailsEntity? =
                mOneDayWeatherViewModel.getDataByCity(mCityName)
            if (currentWeather?.weatherDetails != null) {
                clearAllList()
                mOneDayWeatherViewModel.insertDataIntoDataBase(
                    WeatherDetailsEntity(
                        cityName = currentWeather.cityName,
                        weatherDetails = currentWeather.weatherDetails
                    )
                )
                val temp = fromJsonStringToJsonObject(currentWeather.weatherDetails)
                handleAndSetData(temp)
            }
            else{
                Toast.makeText(
                    requireContext(),
                    getString(R.string.offline_data_is_not_available),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handleWeatherResponse(it: Resource<Temp>?) {
        binding.btnNext.isEnabled = true
        when (it?.status) {
            Status.SUCCESS -> {
                hideProgressDialog()
                clearAllList()
                if (it.data != null) {
                    insertAllData(fromJsonObjectToJsonString(it.data))
                    handleAndSetData(it.data)
                }
            }

            Status.ERROR -> {
                hideProgressDialog()
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }

            Status.LOADING -> {
                requireActivity().showProgressDialog()
            }

            null -> {
                hideProgressDialog()
            }
        }
    }
    private fun refreshData() {
        mOneDayWeatherViewModel.getData(
            Constants.API_KEY,
            Constants.WEATHER_DAYS,
            mCityName,
            "yes",
            "no"
        )
    }
}