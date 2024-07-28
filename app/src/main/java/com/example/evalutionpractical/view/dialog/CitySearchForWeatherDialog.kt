package com.example.evalutionpractical.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.evalutionpractical.Model.City
import com.example.evalutionpractical.Model.Resource
import com.example.evalutionpractical.Model.Status
import com.example.evalutionpractical.adapter.SearchCityAdapter
import com.example.evalutionpractical.common.constants.Constants
import com.example.evalutionpractical.common.extension.hideProgressDialog
import com.example.evalutionpractical.common.extension.showProgressDialog
import com.example.evalutionpractical.databinding.LayoutSearchCityBinding
import com.example.evalutionpractical.listeners.CityListListener
import com.example.evalutionpractical.ui.viewModel.SearchCityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitySearchForWeatherDialog : BottomSheetDialogFragment() {
    private var binding: LayoutSearchCityBinding? = null
    private val mSearchCityViewModel: SearchCityViewModel by viewModels()
    private var mCityList: ArrayList<String> = ArrayList()
    private var mListener : CityListListener? = null
    private var mSearchCityAdapter : SearchCityAdapter? = null

    private var city: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutSearchCityBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        onObservers()
        setUpClickEvents()
    }

    private fun initView() {
        binding?.searchCityVm = mSearchCityViewModel
    }

    private fun setUpClickEvents() {
        mSearchCityViewModel.closeButtonClick.observe(viewLifecycleOwner) {
            dialog?.dismiss()
        }
        mSearchCityViewModel.searchButtonClick.observe(viewLifecycleOwner) {
            setUpOnSearchClick()
        }
    }

    private fun setUpOnSearchClick() {
        mSearchCityViewModel.apply {
            mSearchCityViewModel.apply { getCityLIst(Constants.API_KEY, city) }
            cityList.observe(viewLifecycleOwner) {
                handleResponse(it)
            }
        }
    }

    private fun handleResponse(it: Resource<City>?) {
        when (it?.status) {
            Status.LOADING -> {
               requireActivity().showProgressDialog()
            }
            Status.SUCCESS -> {
                hideProgressDialog()
                mCityList.clear()
                if (it.data != null) {
                    for (city in it.data.indices){
                        mCityList.add(it.data[city].name)
                    }
                       setUpAdapter()
                }
            }
            Status.ERROR -> {
                hideProgressDialog()
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }

            null -> {
                hideProgressDialog()
            }
        }
    }

    private fun setUpAdapter(){
        if (mCityList.isNotEmpty()){
            binding?.rvData?.visibility = View.VISIBLE
            binding?.tvDataNotFound?.visibility = View.GONE
            mSearchCityAdapter = mListener?.let { SearchCityAdapter(requireContext(),mCityList, it,this) }
            binding?.rvData?.adapter = mSearchCityAdapter
        }
        else{
            binding?.tvDataNotFound?.visibility = View.VISIBLE
            binding?.rvData?.visibility = View.GONE
        }
    }

    private fun onObservers() {
        mSearchCityViewModel.apply {
            mSearchCityViewModel.cityName.observe(viewLifecycleOwner) {
                city = it
            }
        }
    }
    fun setUpListener(cityListListener: CityListListener){
        mListener = cityListListener
    }
}