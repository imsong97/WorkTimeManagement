package com.yunho.worktimemanagement.presentation.main

import android.content.Context

interface WorkTimeContract {
    interface View {
        fun showErrorDialog(msg: String)
    }
    interface Presenter {
        fun sendSlackMessage()
        fun insertData(timeArray: List<String>, todayMilli: Long)
        fun dispose()
    }
}