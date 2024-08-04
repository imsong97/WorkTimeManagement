package com.yunho.worktimemanagement.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import org.json.JSONObject

class RemoteConfigUtil {

    private val config by lazy {
        FirebaseRemoteConfig.getInstance()
    }

    companion object {
        private var mInstance: RemoteConfigUtil? = null
        val instance: RemoteConfigUtil?
            get() = synchronized(RemoteConfigUtil::class.java) {
                if (mInstance == null) {
                    mInstance = RemoteConfigUtil()
                }
                return mInstance
            }
    }

    fun init() {
        println("++ Remote Config init ++")
        FirebaseRemoteConfig.getInstance().apply {
            val remoteConfigSettings = FirebaseRemoteConfigSettings
                .Builder()
                .setMinimumFetchIntervalInSeconds(1)
                .build()
            this.setConfigSettingsAsync(remoteConfigSettings)
            this.fetchAndActivate().addOnCompleteListener {
                if (it.isSuccessful) {
                    this.activate()
                    println("++ Remote Config success++")
                } else {
                    it.exception?.printStackTrace()
                }
            }
        }
    }

    fun getLoginUrl(): String = config.getString("login_url")

    fun getUserAgent(): String = config.getString("user_agent")

    fun getBeforeJS(): String = getCrawlingJSObject().getString("before_login")

    fun getAfterJS(): String = getCrawlingJSObject().getString("after_login")

    private fun getCrawlingJSObject(): JSONObject {
        val json = config.getString("crawling_js")
        return JSONObject(json)
    }
}