package com.yunho.worktimemanagement

import android.app.Application
import com.yunho.worktimemanagement.utils.RemoteConfigUtil

class WorkTimeManagementApp : Application() {
    override fun onCreate() {
        super.onCreate()
        RemoteConfigUtil.instance?.init()
    }
}