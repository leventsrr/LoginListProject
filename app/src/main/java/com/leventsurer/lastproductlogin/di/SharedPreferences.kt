package com.leventsurer.lastproductlogin.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SharedPreferences(context: Context) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("myPref", AppCompatActivity.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    fun createSharedPref(isLogin:Boolean){
        editor?.putBoolean("isLogin",isLogin)
        editor?.commit()
    }


    fun isLogin(): Boolean? {
        return sharedPreferences.getBoolean("isLogin", false)
    }

    fun removeData(){
        editor?.clear()
        editor?.commit()
        Log.e("exit","çıktı")
    }




}