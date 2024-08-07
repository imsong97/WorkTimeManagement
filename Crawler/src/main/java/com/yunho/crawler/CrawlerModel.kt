package com.yunho.crawler

data class CrawlerModel(
    val url: String,
    val userAgent: String,
    val id: String,
    val pw: String,
    val beforeLoginJS: String = "",
    val afterLoginJS: String = ""
)
