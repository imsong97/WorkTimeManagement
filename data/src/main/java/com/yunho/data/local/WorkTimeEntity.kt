package com.yunho.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WorkAttendance")
data class WorkTimeListEntity(
    @PrimaryKey(autoGenerate = true)
    val index: Long? = null,
    var date: String? = null, // 2024.08.02 (금)
    var dateForLong: Long? = null, // Locale.now() -> milliseconds
    var startTime: String? = null, // 09:00
    var endTime: String? = null, // 18:00
    var dayOffTime: Int = 0, // 0, 2, 4, 6, 8
    var isCustom: Boolean = false
)
