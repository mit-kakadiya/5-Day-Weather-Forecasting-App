package com.example.evalutionpractical.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.evalutionpractical.Model.OnBoardingModel
import com.example.evalutionpractical.common.constants.Constants
import com.example.evalutionpractical.databinding.ItemBoardingScreenBinding


class OnBoardingScreenAdapter(private val context: Context, private val list: ArrayList<OnBoardingModel>):RecyclerView.Adapter<OnBoardingScreenAdapter.OnBoardingHolder>() {
    class OnBoardingHolder(val binding: ItemBoardingScreenBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnBoardingHolder {
        return OnBoardingHolder(ItemBoardingScreenBinding.inflate(LayoutInflater.from(context),parent,false))
    }
    override fun onBindViewHolder(holder: OnBoardingHolder, position: Int) {
        val data = list[position]
        with(holder){
            binding.apply {

                when(position){
                    Constants.ONBOARDING_1->{
                        ivTop.setImageDrawable(ContextCompat.getDrawable(context,data.imageUrl))
                        tvWelcome.text = data.title
                        tvDescription.text = data.description
                    }
                    Constants.ONBOARDING_2->{
                        ivTop.setImageDrawable(ContextCompat.getDrawable(context,data.imageUrl))
                        tvWelcome.text = data.title
                        tvDescription.text = data.description
                    }
                    Constants.ONBOARDING_3->{
                        ivTop.setImageDrawable(ContextCompat.getDrawable(context,data.imageUrl))
                        tvWelcome.text = data.title
                        tvDescription.text = data.description
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }

}