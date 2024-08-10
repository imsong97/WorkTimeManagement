package com.yunho.crawler

import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient

internal class WorkTimeCrawler(
    private val crawlerListener: CrawlerListener,
    private val webView: WebView
) {

    private var isLogin = false

    fun init(crawlerModel: CrawlerModel) {
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
                        val jsCode =
                            """
                        let onOff = document.getElementsByClassName("badge lg black badge-off");
                        let e = document.getElementsByClassName('btn btn-md line-1');
                        let startTime = e[0].innerText;
                        let endTime = e[1].innerText;
                        if (startTime.startsWith("출근") || endTime.startsWith("퇴근")) {
                            return [startTime, endTime];
                        } else if (onOff.length != 0 && onOff[0].innerText === "OFF") {
                            return ["OFF"];
                        } else {
                            return [];
                        }
                        """
                                .trimIndent()

                        webView.evaluateJavascript("(function() {$jsCode; })();") {
                            val textArray = it.replace("[", "").replace("]", "").split(",")
                            println("+++++evaluateJavascript++")
                            println("${textArray}") // "출근 / 09:51"
                            println("+++++evaluateJavascript++")
                            crawlerListener.loginSuccess(textArray)
                        }
                    } else {
                        isLogin = true
                        val jsCode =
                            """
                        document.getElementById('user_pwd').value = '${crawlerModel.pw}';
                        document.getElementsByClassName('btn_submit')[0].click();
                        """
                                .trimIndent()

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