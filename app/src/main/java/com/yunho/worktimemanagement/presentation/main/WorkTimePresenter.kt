package com.yunho.worktimemanagement.presentation.main

import android.content.Context
import com.yunho.data.SlackRepository
import com.yunho.data.WorkTimeDataRepository
import com.yunho.data.local.WorkTimeListEntity
import com.yunho.worktimemanagement.utils.ContextWrapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class WorkTimePresenter(
    private val view: WorkTimeContract.View,
    private val workTimeDataRepository: WorkTimeDataRepository,
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

    override fun insertData(timeArray: List<String>, todayMilli: Long) {
        workTimeDataRepository.getWorkTimeDataWithDate(todayMilli)
            .subscribeOn(Schedulers.io())
            .map {
                /**
                 * todayMilli로 검색해서 없으면 insert
                 * 있으면 update
                 * */
                val entity = if (it.index == null) {

                } else {

                }
            }
            .subscribe({

            }, {
                it.printStackTrace()
            })
            .also {
                compositeDisposable.add(it)
            }
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}