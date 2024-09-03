package com.yunho.crawler

import android.content.Context
import android.util.Log
import android.webkit.WebView

class WorkTimeCrawlerHelper(
    private val model: CrawlerModel,
    private val callback: CrawlerCallback?
) {

    private val crawlerListener = object : WorkTimeCrawler.CrawlerListener {
        override fun loginSuccess(timeArray: List<String>) {
            Log.e("CrawlerListener", "++loginSuccess : $timeArray++") // empty -> [null] / ["OFF"]
            if (timeArray.isEmpty()) return
            callback?.loginSuccess(timeArray)
        }

        override fun loginFailed() {
            Log.e("CrawlerListener", "++loginFailed++")
            callback?.loginFailed()
        }

        override fun getCookieValue(cookie: String) {
            Log.e("CrawlerListener", "++getCookieValue : $cookie++")
        }
    }

    fun init(context: Context, webView: WebView?) {
        (webView ?: WebView(context)).also {
            WorkTimeCrawler(crawlerListener, it).init(model)
        }
    }

    interface CrawlerCallback {
        fun loginSuccess(timeArray: List<String>)
        fun loginFailed()
    }
}