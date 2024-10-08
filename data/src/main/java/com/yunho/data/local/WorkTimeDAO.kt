package com.yunho.data.local

import androidx.room.*

@Dao
internal interface WorkTimeDAO {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertWorkTime(entity: WorkTimeListEntity): Long

    @Delete
    fun deleteWorkTime(entity: WorkTimeListEntity): Int

    @Query("DELETE FROM WorkAttendance WHERE `index` = :idx")
    fun deleteWorkTime(idx: Long): Int

    @Update
    fun updateWorkTime(entity: WorkTimeListEntity): Int

    @Query("SELECT * FROM WorkAttendance")
    fun getAllList(): List<WorkTimeListEntity>

    @Query("SELECT * FROM WorkAttendance WHERE `index` = :idx")
    fun getWorkTimeData(idx: Long): WorkTimeListEntity

    @Query("SELECT * FROM WorkAttendance WHERE `dateForLong` = :dateForLong")
    fun getWorkTimeDataWithDate(dateForLong: Long): WorkTimeListEntity

    @Query("DELETE FROM WorkAttendance")
    fun deleteALL(): Int
}