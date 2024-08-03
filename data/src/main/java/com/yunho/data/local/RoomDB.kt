package com.yunho.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WorkTimeListEntity::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    internal abstract fun getWorkTimeDAO(): WorkTimeDAO

    companion object {
        private var instance: RoomDB? = null

        fun getInstance(context: Context): RoomDB? {
            synchronized(this) {
                if (instance == null) {
                    instance =  Room.databaseBuilder(context, RoomDB::class.java, "WorkTimeList.db").build()
                }
                return instance
            }
        }
    }
}