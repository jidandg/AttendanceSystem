package com.marwinjidopi.attendancesystem.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marwinjidopi.attendancesystem.data.ContentEntity
import com.marwinjidopi.attendancesystem.databinding.ItemLayoutBinding
import com.marwinjidopi.attendancesystem.ui.home.HomeAdapter.*

class HomeAdapter : RecyclerView.Adapter<Holder>() {

    private var listData = ArrayList<ContentEntity>()

    fun setData(data: List<ContentEntity>) {
        this.listData.clear()
        this.listData.addAll(data)
    }

    inner class Holder (private val bind: ItemLayoutBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(data : ContentEntity){
            with(bind){
                tvClassName.text = data.className
                tvPengajar.text = data.classTeacherI + "" + data.classTeacherII
                tvClock.text = data.classTime
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}