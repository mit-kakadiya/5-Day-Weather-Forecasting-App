package com.example.evalutionpractical.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

abstract class BaseFragment<vb : ViewDataBinding> : Fragment() {
    lateinit var binding: vb
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateLayout(layoutInflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpClickEvents()
        onObservers()
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater, container: ViewGroup?): vb
    abstract fun initView()
    abstract fun setUpClickEvents()
    abstract fun onObservers()
}