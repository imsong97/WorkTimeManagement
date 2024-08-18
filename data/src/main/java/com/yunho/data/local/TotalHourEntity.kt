package com.yunho.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MonthlyTotal")
data class TotalHourEntity(
    val year: Int? = 0, // 2024
    val month: Int? = null, // 7
    val minute: Int? = 0 //
)
