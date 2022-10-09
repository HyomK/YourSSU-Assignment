package com.assignment2.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    val TAG = "Assignment2"
    private val preferences: SharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)

    fun getEmail(defValue: String):String{
        return preferences.getString("email",defValue).toString()
    }

    fun setEmail(defValue: String){
        preferences.edit().putString("email",defValue).apply()
    }

    fun getPassword(defValue: String):String{
        return preferences.getString("pwd",defValue).toString()
    }

    fun setPassword(defValue: String){
        preferences.edit().putString("pwd",defValue).apply()
    }
}