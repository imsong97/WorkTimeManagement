package com.yunho.worktimemanagement.presentation

interface Contract {
    interface View {

    }
    interface Presenter {
        fun sendSlackMessage()
    }
}