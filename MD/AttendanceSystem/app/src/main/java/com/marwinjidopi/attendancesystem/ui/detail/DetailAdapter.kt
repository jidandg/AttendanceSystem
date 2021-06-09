package com.marwinjidopi.attendancesystem.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marwinjidopi.attendancesystem.data.entity.ClassEntity
import com.marwinjidopi.attendancesystem.databinding.ClassLayoutBinding
import kotlin.collections.ArrayList

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.Holder>() {

    private var userList = ArrayList<ClassEntity>()

    fun setData(data: ClassEntity) {
        this.userList.clear()
        this.userList.addAll(listOf(data))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val bind = ClassLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(bind)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class Holder(private val binding: ClassLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ClassEntity) {
            with(binding) {
                tvClassName.text = data.className
                tvLink.text = data.classLink
                tvClock.text = data.classDate + "|" + data.classTime + "WIB"
            }
        }
    }
}