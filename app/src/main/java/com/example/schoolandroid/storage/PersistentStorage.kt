package com.example.schoolandroid.storage

import android.content.Context
import android.content.SharedPreferences

object PersistentStorage {
    private const val STORAGE_NAME = "SamotokhinSchool"

    private lateinit var settings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun init(cntxt: Context) {
        val context = cntxt
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        editor = settings.edit()
    }

    // put from key {name : value}
    fun addProperty(name : String, value : String) {
        editor.putString(name, value)
        editor.apply()
    }

    // get from key -> return value
    fun getProperty(name : String) : String? {
        return settings.getString(name, null)
    }
}