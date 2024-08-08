package com.yunho.worktimemanagement.presentation.main

import android.content.Context
import com.yunho.data.SlackRepository
import com.yunho.data.WorkTimeDataRepository
import com.yunho.data.local.WorkTimeListEntity
import com.yunho.worktimemanagement.utils.ContextWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class WorkTimePresenter(
    private val view: WorkTimeContract.View,
    private val repository: WorkTimeDataRepository,
    private val contextWrapper: ContextWrapper
) : WorkTimeContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun sendSlackMessage() {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = Date()
        val currentTime = format.format(time)
        SlackRepository.instance?.sendSlackMessage(currentTime)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                println("++sendSlackMessage : $it++")
            }, {
                it.printStackTrace()
            })?.also {
                compositeDisposable.add(it)
            }
    }

    override fun insertData(context: Context) {
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}