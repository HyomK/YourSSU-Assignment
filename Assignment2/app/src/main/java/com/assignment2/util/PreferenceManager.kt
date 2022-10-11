package com.assignment2.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.assignment2.data.Account
import com.assignment2.data.AccountRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class PreferenceManager @Inject constructor(context: Context) {
    private val TAG = "Assignment2"
    private val preferences: SharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    val gson = Gson()

    fun setEmail(email: String){
        var account: Account?=null
        preferences.getString("account",null).let{ it ->
            if(it.isNullOrEmpty()){
                account = Account(email=email)
            }
            else{
                account = getAccount()?.apply {
                   this.email = email
                }
            }
        }
        preferences.edit().putString("account",gson.toJson(account,Account::class.java)).apply()
    }

    fun setPassword(pwd: String){
        var account: Account?=null
        preferences.getString("account",null).let{ it ->
            account = if(it.isNullOrEmpty()){
                Account(password=pwd)
            } else{
                getAccount()?.copy(password = pwd)
            }
        }
        preferences.edit().putString("account",gson.toJson(account,Account::class.java)).apply()
    }

    fun getAccount():Account?{
        preferences.getString("account",null).also {
            return if(it.isNullOrEmpty()) null
            else gson.fromJson(preferences.getString("account",""),Account::class.java)
        }
    }

    fun clear(){
        preferences.edit().clear().commit()
    }
}

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {

    @Provides
    fun bindPreferenceManger(@ApplicationContext context: Context):PreferenceManager
        = PreferenceManager(context)
}