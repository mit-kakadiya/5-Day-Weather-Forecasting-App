package com.example.evalutionpractical.common.utills

import com.example.evalutionpractical.Model.OnBoardingModel
import com.example.evalutionpractical.R

class DummyDataGenerator {

    companion object {
        fun getOnBoardingData(): ArrayList<OnBoardingModel> {
            return arrayListOf(
                OnBoardingModel(
                    "1",
                    "Welcome to Weather Pro!",
                    R.drawable.ic_winter,
                    "welcome to weather app and Get accurate 5-day weather forecasts"
                ),
                OnBoardingModel(
                    "2",
                    "Key Features",
                    R.drawable.ic_summer,
                    "Get accurate 5-day weather forecasts to plan your week ahead."
                ),
                OnBoardingModel(
                    "3",
                    "Get Started",
                    R.drawable.ic_monsoon,
                    "Tap 'Get Started' to begin your weather journey with WeatherPro!"
                ),
            )
        }
    }
}
