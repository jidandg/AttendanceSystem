package com.marwinjidopi.attendancesystem.ui.home

import androidx.lifecycle.ViewModel
import com.marwinjidopi.attendancesystem.data.ContentEntity
import com.marwinjidopi.attendancesystem.utils.DataDummy

class HomeViewModel : ViewModel() {
    fun getListClass(): List<ContentEntity> = DataDummy.generateDummyData()
}