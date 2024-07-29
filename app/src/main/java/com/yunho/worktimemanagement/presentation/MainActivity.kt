package com.yunho.worktimemanagement.presentation

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.yunho.worktimemanagement.databinding.ActivityMainBinding
import com.yunho.worktimemanagement.presentation.worker.SlackWorker
import com.yunho.worktimemanagement.utils.RemoteConfigUtil
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), Contract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: Contract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = Presenter()
        startWorkManager()
    }

    private fun startWorkManager() {
        Log.e("startWorkManager", "++startWorkManager++")
        WorkManager.getInstance(this).cancelAllWork()
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 20)
        calendar.set(Calendar.MINUTE, 40)
        calendar.set(Calendar.SECOND, 0)

        var initialDelay: Long = calendar.timeInMillis - System.currentTimeMillis()
        if (initialDelay < 0) {
            initialDelay += TimeUnit.DAYS.toMillis(1)
        }

        val workRequest = PeriodicWorkRequest
            .Builder(SlackWorker::class.java, 24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }
}