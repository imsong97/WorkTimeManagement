package com.yunho.worktimemanagement.presentation

import com.yunho.data.SlackRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class Presenter : Contract.Presenter {

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
}