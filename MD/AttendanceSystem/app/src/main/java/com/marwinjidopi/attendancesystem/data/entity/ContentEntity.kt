package com.marwinjidopi.attendancesystem.data.entity

import java.util.*

data class ContentEntity(
    var id: String,
    var className: String,
    var classTeacherI: String,
    var classTeacherII: String,
    var classDate: String,
    var classTime: String,
    var classInfo: String
)