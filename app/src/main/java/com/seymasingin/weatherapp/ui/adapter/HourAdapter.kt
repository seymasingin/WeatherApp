package com.seymasingin.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seymasingin.weatherapp.data.model.Hour
import com.seymasingin.weatherapp.databinding.HourBinding
import jp.wasabeef.glide.transformations.GrayscaleTransformation

class HourAdapter(): RecyclerView.Adapter<HourAdapter.HourHolder> () {

    private val hourList = ArrayList<Hour>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder {
        val binding = HourBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return HourHolder(binding)
    }

    override fun onBindViewHolder(holder: HourHolder, position: Int) {
        holder.bind(hourList[position])
    }

    class HourHolder( private val binding: HourBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(hour: Hour) {
            val dateTimeString = hour.time
            val dateTimeParts = dateTimeString.split(" ")
            val time = dateTimeParts[1].split(":")[0]
            binding.textHour.text = time
            Glide.with(binding.iconHour).load("https:" + hour.condition.icon).into(binding.iconHour)
            Glide.with(binding.iconHour)
                .load("https:" + hour.condition.icon)
                .apply(RequestOptions().transform(GrayscaleTransformation()))
                .into(binding.iconHour)
            binding.textTemp.text = hour.temp_c.toInt().toString()
        }
    }

    override fun getItemCount(): Int {
        return hourList.size
    }

    fun updateList(list: List<Hour>) {
        hourList.clear()
        hourList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}