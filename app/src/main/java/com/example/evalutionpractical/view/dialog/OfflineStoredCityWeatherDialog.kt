package com.example.evalutionpractical.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.evalutionpractical.adapter.SearchCityAdapter
import com.example.evalutionpractical.databinding.LayoutStoredCityListBinding
import com.example.evalutionpractical.listeners.CityListListener
import com.example.evalutionpractical.ui.viewModel.OfflineStoredDataViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfflineStoredCityWeatherDialog : BottomSheetDialogFragment() {

    var binding: LayoutStoredCityListBinding? = null
    private var mCityList: ArrayList<String> = ArrayList()
    private var mListener: CityListListener? = null
    private var mSearchCityAdapter: SearchCityAdapter? = null
    private val mOfflineStoredDataViewModel: OfflineStoredDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutStoredCityListBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        onObservers()
        setUpClickEvents()
    }

    private fun setUpClickEvents() {
        mOfflineStoredDataViewModel.closeButtonClick.observe(viewLifecycleOwner) {
            dialog?.dismiss()
        }
    }

    private fun onObservers() {
        mOfflineStoredDataViewModel.getCityLIst().observe(viewLifecycleOwner) {
            if (it != null) {
                for (i in it.indices) {
                    it[i]?.let { it1 -> mCityList.add(it1) }
                }
                setUpAdapter()
            }
        }
    }

    private fun setUpAdapter() {
        if (mCityList.isNotEmpty()) {
            binding?.tvDataNotFound?.visibility = View.GONE
            mSearchCityAdapter =
                mListener?.let { SearchCityAdapter(requireContext(), mCityList, it, this) }
            binding?.rvData?.adapter = mSearchCityAdapter
        } else {
            binding?.tvDataNotFound?.visibility = View.VISIBLE
        }
    }

    private fun initView() {
        binding?.offlineStoredDataVm = mOfflineStoredDataViewModel
    }

    fun setUpListener(cityListListener: CityListListener) {
        mListener = cityListListener
    }

}