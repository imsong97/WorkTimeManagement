package com.yunho.worktimemanagement.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class TimeEventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, i: Intent?) {
        context?.let {
            val workRequest = OneTimeWorkRequest.Builder(SlackMessageWorker::class.java).build()
            WorkManager.getInstance(it).enqueue(workRequest)
        }
    }
}