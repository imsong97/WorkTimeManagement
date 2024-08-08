package com.yunho.worktimemanagement.presentation.main

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.yunho.crawler.CrawlerModel
import com.yunho.crawler.GroupwareCrawler
import com.yunho.data.WorkTimeDataRepository
import com.yunho.worktimemanagement.databinding.ActivityWorkTimeMainBinding
import com.yunho.worktimemanagement.utils.ContextWrapper
import com.yunho.worktimemanagement.utils.PreferenceWrapper
import com.yunho.worktimemanagement.utils.RemoteConfigUtil
import com.yunho.worktimemanagement.worker.SlackWorker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class WorkTimeMainActivity : AppCompatActivity(), WorkTimeContract.View {

    private lateinit var binding: ActivityWorkTimeMainBinding
    private lateinit var presenter: WorkTimeContract.Presenter
    private val preferenceWrapper by lazy { PreferenceWrapper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkTimeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = WorkTimePresenter(this, WorkTimeDataRepository(this), ContextWrapper(this))
        initView()
//        startWorkManager()

        startCrawler()
    }

    private fun initView() {
        showProgressbar()

        binding.today.text = run {
            val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd (E)", Locale.getDefault())
            simpleDateFormat.format(Date(System.currentTimeMillis()))
        }

        binding.monthSetting.setOnClickListener {
            // TODO
        }

        binding.btnShowLastData.setOnClickListener {
            // TODO
        }
    }

    private fun startCrawler() {
        object : GroupwareCrawler.CrawlerListener {
            override fun loginSuccess(timeArray: List<String>) {
                Log.e("CrawlerListener", "++loginSuccess : $timeArray++") // empty -> [null]
                hideProgressbar()
//                binding.webview.visibility = View.GONE
                // TODO
                if (timeArray.isEmpty()) return

            }

            override fun loginFailed() {
                hideProgressbar()
            }

            override fun getCookieValue(cookie: String) {

            }
        }.also {
            val url = RemoteConfigUtil.instance?.getLoginUrl() ?: ""
            val userAgent = RemoteConfigUtil.instance?.getUserAgent() ?: ""
            if (url.isEmpty() || userAgent.isEmpty()) {
                // TODO error dialog
                return
            }
            val id = preferenceWrapper.getId().ifEmpty { intent.getStringExtra("login_id") ?: "" }
            val pw = preferenceWrapper.getPassword().ifEmpty { intent.getStringExtra("login_pw") ?: "" }
            val model = CrawlerModel(
                url = String.format(url, id),
                userAgent = userAgent,
                id = id,
                pw = pw
            )
            GroupwareCrawler(it, binding.webview).init(model)
        }
    }

    private fun startWorkManager() {
        Log.e("startWorkManager", "++startWorkManager++")
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 21)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        var initialDelay: Long = calendar.timeInMillis - System.currentTimeMillis()
        if (initialDelay < 0) {
            initialDelay += TimeUnit.DAYS.toMillis(1)
        }

        val workRequest = PeriodicWorkRequest
            .Builder(SlackWorker::class.java, 24, TimeUnit.HOURS)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("SlackNoti", ExistingPeriodicWorkPolicy.REPLACE, workRequest)
    }

    private fun showProgressbar() {
        binding.loadingBar.visibility = View.VISIBLE
    }

    private fun hideProgressbar() {
        binding.loadingBar.visibility = View.GONE
    }

    override fun showErrorDialog(msg: String) {
        AlertDialog.Builder(this)
            .setMessage(msg)
            .setPositiveButton("확인", null)
            .show()
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }
}