package com.yunho.worktimemanagement.presentation.main

import android.content.Context

interface WorkTimeContract {
    interface View {
        fun showErrorDialog(msg: String)
    }
    interface Presenter {
        fun sendSlackMessage()
        fun insertData(context: Context)
        fun dispose()
    }
}