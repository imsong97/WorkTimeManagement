package com.yunho.crawler

import android.content.Context
import android.webkit.WebView

class PageCrawler(
    private val crawlerListener: CrawlerListener,
    private val webView: WebView
) {

    fun init(context: Context) {

    }

    interface CrawlerListener {
        fun loginSuccess()
        fun loginFailed()
    }
}