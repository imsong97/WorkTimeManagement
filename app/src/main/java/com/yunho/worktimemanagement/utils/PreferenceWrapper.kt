package com.yunho.worktimemanagement.utils

import android.app.Activity
import android.content.Context

class PreferenceWrapper(private val context: Context) {

    private val PREF_NAME = "APP_PREF"

    private val IS_AUTO_LOGIN = "auto_login"
    private val LOGIN_ID = "login_id"
    private val LOGIN_PW = "login_pw"

    private val pref = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE)

    fun isAutoLogin() = pref.getBoolean(IS_AUTO_LOGIN, false)

    fun setAutoLogin(value: Boolean) {
        pref.edit().putBoolean(IS_AUTO_LOGIN, value).apply()
    }

    fun getId(): String = pref.getString(LOGIN_ID, "") ?: ""

    fun setId(id: String) {
        pref.edit().putString(LOGIN_ID, id).apply()
    }

    fun getPassword(): String = pref.getString(LOGIN_PW, "") ?: ""

    fun setPassword(pw: String) {
        pref.edit().putString(LOGIN_PW, pw).apply()
    }
}