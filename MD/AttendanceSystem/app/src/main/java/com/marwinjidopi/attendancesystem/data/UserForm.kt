package com.marwinjidopi.attendancesystem.data

import android.graphics.Bitmap

data class UserForm(
    var img: Bitmap,
    var name: String,
    var nim: String,
    var semester: String,
    var faculty: String,
    var major: String
)
