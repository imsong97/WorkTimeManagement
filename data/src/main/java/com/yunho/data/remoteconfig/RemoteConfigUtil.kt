package com.yunho.data.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class RemoteConfigUtil {

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

    fun getLoginUrl() = FirebaseRemoteConfig.getInstance().getString("login_url")
}