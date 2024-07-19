package com.yunho.crawler

import android.content.Context
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class GroupwareCrawler(
    private val crawlerListener: CrawlerListener,
    private val webView: WebView
) {

    private var isLogin = false
    private val password = "" // TODO 삭제
    private val LOGIN_URL = "" // TODO remote config

    fun init(context: Context) {
        webView.apply {
            this.settings.javaScriptEnabled = true
            // TODO remote config
            this.settings.userAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36"

            this.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    println("++++++++++onPageFinished+++++++++++")
                    println(url)

                    if (isLogin) {
                        val jsCode =
                            """
                        let e = document.getElementsByClassName('btn btn-md line-1');
                        let workText = [e[0].innerText, e[1].innerText];
                        return workText;
                        """
                                .trimIndent()

                        webView.evaluateJavascript("(function() {$jsCode; })();") {
                            val textArray = it.replace("[", "").replace("]", "").split(",")
                            println("+++++evaluateJavascript++")
                            println("${textArray[0]}") // "출근 / 09:51"
                            println("+++++evaluateJavascript++")
                        }
                    } else {
                        isLogin = true
                        val jsCode =
                            """
                        document.getElementById('user_pwd').value = '${password}';
                        document.getElementsByClassName('btn_submit')[0].click();
                        """
                                .trimIndent()

                        webView.evaluateJavascript(jsCode, null)
                    }
                }
            }
        }
        webView.loadUrl(LOGIN_URL)
    }

    interface CrawlerListener {
        fun loginSuccess()
        fun loginFailed()
        fun getCookieValue(cookie: String)
    }
}