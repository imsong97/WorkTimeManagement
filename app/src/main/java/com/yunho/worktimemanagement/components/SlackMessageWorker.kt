package com.yunho.worktimemanagement.components

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yunho.data.SlackRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class SlackMessageWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.e("doWork", "++doWork()++")
        /**
         * [조건]
         * 커스텀이면 크롤링 하지 않는다
         * */
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val time = Date()
        val currentTime = format.format(time)
        return SlackRepository.instance?.sendSlackMessage("$currentTime : use worker")
            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.doOnSuccess { _ -> }
//            ?.doOnError { _ -> }
            ?.map { _ -> Result.success() }
            ?.onErrorReturnItem(Result.failure())
            ?.blockingGet() ?: Result.success()
    }
}