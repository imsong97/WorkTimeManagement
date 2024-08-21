package com.yunho.worktimemanagement

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import com.yunho.worktimemanagement.utils.RemoteConfigUtil
import com.yunho.worktimemanagement.components.TimeEventReceiver


class WorkTimeManagementApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RemoteConfigUtil.instance?.init()
//        PreferenceWrapper(this).setAutoLogin(false)
        setAlarmManager()
    }

    fun setAlarmManager() {
        Log.e("WorkTimeManagementApp", "++setAlarmManager++")
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, TimeEventReceiver::class.java)
        var pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_NO_CREATE)
        }

        // 최초 등록
        if (pendingIntent == null) {
            pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            } else {
                PendingIntent.getBroadcast(this, 0, intent, 0)
            }
        }

        val calendar = Calendar.getInstance().apply {
            this.set(Calendar.HOUR_OF_DAY, 18)
            this.set(Calendar.MINUTE, 0)
            this.set(Calendar.SECOND, 0)
            if (this.timeInMillis < System.currentTimeMillis()) {
                this.add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

    }
}