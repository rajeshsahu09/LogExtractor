package com.rzsahu.logextractor

import android.content.Context
import android.content.SharedPreferences


class SharedPreferenceLogService(context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var isServiceRunning: Boolean
        get() = sharedPreferences.getBoolean(KEY_BOOLEAN_VALUE, false)
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(KEY_BOOLEAN_VALUE, value)
            editor.apply()
        }

    companion object {
        private const val PREF_NAME = "log_service_state"
        private const val KEY_BOOLEAN_VALUE = "isRunning"
    }
}

