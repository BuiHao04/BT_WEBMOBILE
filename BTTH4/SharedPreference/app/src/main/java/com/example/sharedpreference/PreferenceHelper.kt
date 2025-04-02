package com.example.sharedpreference

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context:Context) {
    private val sharedPreferences:SharedPreferences = context.getSharedPreferences("UserPrefs",Context.MODE_PRIVATE)
    fun saveUser(name:String,password:String){
        sharedPreferences.edit().apply {
            putString("userName", name)
            putString("userPassword", password)
            apply()
    }
    }
    fun getUserName():String{
        return sharedPreferences.getString("userName","")?:""
    }
    fun getPassword():String{
        return sharedPreferences.getString("userPassword","")?:""

    }
    fun clearData(){
        sharedPreferences.edit().clear().apply()
    }
}