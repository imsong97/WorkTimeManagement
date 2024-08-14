package com.yunho.worktimemanagement.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yunho.worktimemanagement.WorkTimeManagementApp

class DeviceBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, i: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == i?.action) {
            (context?.applicationContext as? WorkTimeManagementApp)?.setAlarmManager()
        }
    }
}