package com.example.evalutionpractical.ui.activity

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.WindowManager
import com.example.evalutionpractical.Model.OnBoardingModel
import com.example.evalutionpractical.adapter.OnBoardingScreenAdapter
import com.example.evalutionpractical.common.utills.DummyDataGenerator
import com.example.evalutionpractical.databinding.ActivityOnBoardingBinding


class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {
    private var mList: ArrayList<OnBoardingModel>? = null
    private var mAdapter: OnBoardingScreenAdapter? = null

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityOnBoardingBinding {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
        )
        window.statusBarColor = Color.parseColor("#4878c2")
        return ActivityOnBoardingBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setUpList()
        setUpAdapter()
    }

    override fun setUpClickEvents() {
        binding.btnGetStarted.setOnClickListener {
            Intent(this, WeatherActivity::class.java).apply {
                startActivity(this)
                finishAffinity()
            }
        }
    }

    override fun onObservers() {

    }

    private fun setUpList() {
        mList = DummyDataGenerator.getOnBoardingData()
    }

    private fun setUpAdapter() {
        mAdapter = mList?.let { OnBoardingScreenAdapter(this, it) }
        binding.pagerView.adapter = mAdapter
        binding.pagerView.let { binding.indicator.setupWithViewPager(it) }
    }
}