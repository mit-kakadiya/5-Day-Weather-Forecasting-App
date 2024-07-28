package com.example.evalutionpractical.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.evalutionpractical.databinding.ItemCityLayoutBinding
import com.example.evalutionpractical.listeners.CityListListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchCityAdapter(val context: Context, private val cityList:ArrayList<String>, private val mCityListListener: CityListListener,
                        private val citySearchForWeatherDialog: BottomSheetDialogFragment
):RecyclerView.Adapter<SearchCityAdapter.SearchCityViewHolder>(){
    class SearchCityViewHolder(val binding:ItemCityLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchCityViewHolder {
        return SearchCityViewHolder(ItemCityLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchCityViewHolder, position: Int) {
        holder.binding.tvCityName.text = cityList[position]
        holder.binding.layoutParent.setOnClickListener {
            mCityListListener.onCitySelected(cityList[position])
            citySearchForWeatherDialog.dismiss()
        }
    }

    override fun getItemCount(): Int {
       return cityList.size
    }
}