package com.yunho.crawler

import android.content.Context
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient

class GroupwareCrawler(
    private val crawlerListener: CrawlerListener,
    private val webView: WebView
) {

    private var isLogin = false

    fun init(context: Context, crawlerModel: CrawlerModel) {
        println("++++++++++CrawlerModel : ${crawlerModel}+++++++++++")
        deleteCookie()

        webView.apply {
            this.settings.javaScriptEnabled = true

            this.settings.userAgentString = crawlerModel.userAgent

            this.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    println("++++++++++onPageFinished+++++++++++")
                    println(url)

                    if (isLogin) {
                        val jsCode = crawlerModel.afterLoginJS.trimIndent()

                        webView.evaluateJavascript("(function() {$jsCode; })();") {
                            val textArray = it.replace("[", "").replace("]", "").split(",")
                            println("+++++evaluateJavascript++")
                            println("${textArray[0]}") // "출근 / 09:51"
                            println("+++++evaluateJavascript++")
                            crawlerListener.loginSuccess(textArray)
                        }
                    } else {
                        isLogin = true
                        val jsCode = crawlerModel.beforeLoginJS.trimIndent()

                        webView.evaluateJavascript(jsCode, null)
                    }
                }
            }
        }
        webView.loadUrl(crawlerModel.url)
    }

    private fun deleteCookie() {
        CookieManager.getInstance().also {
            it.removeAllCookies(null)
            it.flush()
        }
    }

    interface CrawlerListener {
        fun loginSuccess(timeArray: List<String>)
        fun loginFailed()
        fun getCookieValue(cookie: String)
    }
}