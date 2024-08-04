package com.yunho.worktimemanagement.utils

import android.content.Context
import androidx.core.content.ContextCompat

class ContextWrapper(private val context: Context) {

    fun getString(resId: Int) = context.getString(resId)

    fun getDrawable(resId: Int) = ContextCompat.getDrawable(context, resId)
}