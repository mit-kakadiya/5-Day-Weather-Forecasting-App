package com.example.evalutionpractical.ui.activity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.WindowManager
import com.example.evalutionpractical.R
import com.example.evalutionpractical.databinding.ActivityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class  WeatherActivity : BaseActivity<ActivityWeatherBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityWeatherBinding {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            )
        window.statusBarColor = Color.parseColor("#4878c2")
      return ActivityWeatherBinding.inflate(layoutInflater)

    }

    override fun initView() {

    }

    override fun setUpClickEvents() {

    }

    override fun onObservers() {

    }
}