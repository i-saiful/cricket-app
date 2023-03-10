package com.saiful.cricketapp.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class UtilsSharedPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("fragment_utils", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}