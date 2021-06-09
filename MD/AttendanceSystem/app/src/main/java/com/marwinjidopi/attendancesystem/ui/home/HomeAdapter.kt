package com.marwinjidopi.attendancesystem.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avatarfirst.avatargenlib.AvatarConstants
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.marwinjidopi.attendancesystem.data.entity.ContentEntity
import com.marwinjidopi.attendancesystem.databinding.ItemLayoutBinding
import com.marwinjidopi.attendancesystem.ui.detail.DetailActivity
import com.marwinjidopi.attendancesystem.ui.home.HomeAdapter.*

class HomeAdapter : RecyclerView.Adapter<Holder>() {

    private var listData = ArrayList<ContentEntity>()

    fun setData(data: List<ContentEntity>) {
        this.listData.clear()
        this.listData.addAll(data)
    }

    inner class Holder(private val bind: ItemLayoutBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: ContentEntity) {
            with(bind) {
                imgClass.setImageDrawable(
                    AvatarGenerator.avatarImage(
                        itemView.context,
                        200,
                        AvatarConstants.CIRCLE,
                        data.className
                    )
                )
                tvClassName.text = data.className
                tvPengajar.text = data.classTeacherI + " | " + data.classTeacherII
                tvClock.text = data.classTime
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_CONTENT, data.id)
                    itemView.context.startActivity(intent)
                }
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