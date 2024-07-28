package com.example.evalutionpractical.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.evalutionpractical.R
import com.example.evalutionpractical.common.constants.Constants
import com.example.evalutionpractical.databinding.FragmentFiveDayWeatherBinding
import com.example.evalutionpractical.ui.activity.WeatherActivity
import com.example.evalutionpractical.ui.viewModel.FiveDayWeatherViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class FiveDayWeatherFragment : BaseFragment<FragmentFiveDayWeatherBinding>() {
    private val mFiveDayWeatherViewModel: FiveDayWeatherViewModel by viewModels()
    private var weatherEntriesArrayList: ArrayList<Entry> = arrayListOf()
    private val days = mutableListOf<String>()
    private val date = mutableListOf<String>()
    private val calendar: Calendar = Calendar.getInstance()
    private val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("dd/MM",Locale.getDefault())
    override fun inflateLayout(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFiveDayWeatherBinding {
        return FragmentFiveDayWeatherBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.fiveDayVm = mFiveDayWeatherViewModel
        setUpToolBar()
    }

    override fun setUpClickEvents() {
    }

    override fun onObservers() {
        setTopXAxis()
        getDataFromBundle()
        getWeatherChart()
    }

    @SuppressLint("SetTextI18n")
    private fun setTopXAxis() {
        for (i in 0..4) {
            days.add(dayFormat.format(calendar.time))
            date.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        binding.apply {
            val dayTextViews = listOf(tvDay1, tvDay2, tvDay3, tvDay4, tvDay5)
            val dateTextViews = listOf(tvDate1, tvDate2, tvDate3, tvDate4, tvDate5)
            val windSpeedViews = listOf(tvSpeed1,tvSpeed2,tvSpeed3,tvSpeed4,tvSpeed5)
            mFiveDayWeatherViewModel.tempListVm.observe(viewLifecycleOwner){
            for (i in it.indices) {
                dayTextViews[i].text = days[i]
                dateTextViews[i].text = date[i]
                windSpeedViews[i].visibility = View.VISIBLE
                dateTextViews[i].visibility = View.VISIBLE
                dayTextViews[i].visibility = View.VISIBLE
                    windSpeedViews[i].text = it[i]+"km/h"
                }

            }
        }
    }

    private fun getWeatherChart() {
        weatherEntriesArrayList.clear()
        binding.lineChart.clear()
        binding.lineChart.invalidate()
        binding.lineChart.legend.resetCustom()
        val data = mFiveDayWeatherViewModel.tempListVm.value
        data?.forEachIndexed { index, i ->
            weatherEntriesArrayList.add(Entry(index.toFloat(), i.toFloat()))
        }
        setChatData()
    }

    private fun setChatData() {
        binding.apply {
            val dataSet = LineDataSet(weatherEntriesArrayList, "Sample Data").apply {
                color = ContextCompat.getColor(requireContext(),R.color.gradient_start)
                valueTextColor = Color.WHITE
                lineWidth = 2f
                setDrawValues(true)
                circleRadius = 8f
                valueTextSize = 10f
                setCircleColor(ContextCompat.getColor(requireContext(),R.color.circle_color))
                circleHoleColor = ContextCompat.getColor(requireContext(),R.color.circle_color)
                setDrawFilled(true)
                fillDrawable = ContextCompat.getDrawable(requireContext(),R.drawable.graph_gradient_bg)
            }


            val lineData = LineData(dataSet)
            lineChart.data = lineData

            // Configure top X-axis for days
            lineChart.xAxis.apply {
                isEnabled = false
            }

            // Disable left Y-axis
            lineChart.axisLeft.apply {
                isEnabled = false
            }

            // Disable right Y-axis
            lineChart.axisRight.apply {
                isEnabled = false
            }

            lineChart.description.isEnabled = false
            lineChart.legend.isEnabled = false
            lineChart.setTouchEnabled(true)
            lineChart.setPinchZoom(true)
            lineChart.invalidate() // Refresh chart
        }
    }

    private fun getDataFromBundle() {
        mFiveDayWeatherViewModel.apply {
            tempListVm.value =
                arguments?.getStringArrayList(Constants.TEMP_LIST) as ArrayList<String>
            dateListVm.value =
                arguments?.getStringArrayList(Constants.WIND_SPEED_LIST) as ArrayList<String>
        }
    }

    private fun setUpToolBar() {
        (activity as WeatherActivity).apply {
            this.getBindingFromActivity().apply {
                layoutToolBar.apply {
                    ivBack.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            findNavController().popBackStack()
                        }
                    }
                    ivPlus.visibility = View.INVISIBLE
                    ivMenu.visibility = View.INVISIBLE
                }
            }
        }
    }

}