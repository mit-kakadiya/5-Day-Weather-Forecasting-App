package com.example.evalutionpractical.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<vb:ViewDataBinding> : AppCompatActivity() {
    lateinit var binding:vb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        initView()
        setUpClickEvents()
        onObservers()
    }

   abstract fun inflateLayout(layoutInflater: LayoutInflater):vb
    abstract fun initView()
    abstract fun setUpClickEvents()
    abstract fun onObservers()

    fun getBindingFromActivity():vb{
        return binding
    }
}