package com.marwinjidopi.attendancesystem.ui.detail

import androidx.lifecycle.ViewModel
import com.marwinjidopi.attendancesystem.data.entity.ClassEntity
import com.marwinjidopi.attendancesystem.data.entity.ContentEntity
import com.marwinjidopi.attendancesystem.utils.DataDummy

class DetailViewModel : ViewModel() {
    private lateinit var dataId: String

    fun setSelectedData(dataId: String) {
        this.dataId = dataId
    }

    fun getLastClass(): ClassEntity {
        lateinit var data: ClassEntity
        val entity = DataDummy.generateLastClassDummyData()
        for (entity in entity) {
            if (entity.id == dataId) {
                data = entity
            }
        }
        return data
    }

    fun getNowClass(): ClassEntity {
        lateinit var data: ClassEntity
        val entity = DataDummy.generateOngoingClassDummyData()
        for (entity in entity) {
            if (entity.id == dataId) {
                data = entity
            }
        }
        return data
    }

    fun getNextClass(): ClassEntity {
        lateinit var data: ClassEntity
        val entity = DataDummy.generateNextClassDummyData()
        for (entity in entity) {
            if (entity.id == dataId) {
                data = entity
            }
        }
        return data
    }

    fun getData(): ContentEntity {
        lateinit var data: ContentEntity
        val entity = DataDummy.generateDummyData()
        for (entity in entity) {
            if (entity.id == dataId) {
                data = entity
            }
        }
        return data
    }
}
