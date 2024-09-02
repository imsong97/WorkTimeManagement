package com.yunho.data

import android.content.Context
import com.yunho.data.local.RoomDB
import com.yunho.data.local.WorkTimeDAO
import com.yunho.data.local.WorkTimeListEntity
import io.reactivex.Single

class WorkTimeDataRepository(private val context: Context) {

    private val workTimeDAO: WorkTimeDAO?

    init {
        val db = RoomDB.getInstance(context)
        workTimeDAO = db?.getWorkTimeDAO()
    }

    companion object {
        private var instance: WorkTimeDataRepository? = null

        fun getInstance(context: Context): WorkTimeDataRepository? {
            synchronized(this) {
                if (instance == null) {
                    instance =  WorkTimeDataRepository(context)
                }
                return instance
            }
        }
    }

    fun insertWorkTime(entity: WorkTimeListEntity) =
        Single.create {
            val result = workTimeDAO?.insertWorkTime(entity) ?: -1L
            it.onSuccess(result)
        }

    fun updateWorkTime(entity: WorkTimeListEntity) =
        Single.create {
            val result = workTimeDAO?.updateWorkTime(entity) ?: -1L
            it.onSuccess(result)
        }

    fun deleteWorkTime(idx: Long) =
        Single.create {
            val result = workTimeDAO?.deleteWorkTime(idx) ?: -1
            it.onSuccess(result)
        }

    fun getAllList() =
        Single.create {
            val list = workTimeDAO?.getAllList() ?: listOf()
            it.onSuccess(list)
        }

    fun getWorkTimeData(idx: Long) =
        Single.create {
            val data = workTimeDAO?.getWorkTimeData(idx) ?: WorkTimeListEntity()
            it.onSuccess(data)
        }

    fun getWorkTimeDataWithDate(date: Long) =
        Single.create {
            val data = workTimeDAO?.getWorkTimeDataWithDate(date) ?: WorkTimeListEntity()
            it.onSuccess(data)
        }

    fun deleteAll() =
        Single.create {
            val result = workTimeDAO?.deleteALL() ?: -1
            it.onSuccess(result)
        }
}