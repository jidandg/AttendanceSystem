package com.marwinjidopi.attendancesystem.ui.detail

import androidx.lifecycle.ViewModel
import com.marwinjidopi.attendancesystem.data.ContentEntity
import com.marwinjidopi.attendancesystem.utils.DataDummy

class DetailViewModel : ViewModel(){
    private lateinit var dataId: String

    fun setSelectedData(dataId: String){
        this.dataId = dataId
    }

    fun getData(): ContentEntity{
        lateinit var data: ContentEntity
        val entity = DataDummy.generateDummyData()
        for (entity in entity){
            if (entity.id == dataId){
                data = entity
            }
        }
        return data
    }
}