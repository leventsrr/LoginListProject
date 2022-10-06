package com.leventsurer.lastproductlogin.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SharedPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("myPref", AppCompatActivity.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    //function used to create sharedPreferences if there is no previously created data
    fun createSharedPref(isLogin:Boolean){
        editor.putBoolean("isLogin",isLogin)
        editor.commit()
    }

    //if this methods return true,shared preferences has login data
    fun isLogin(): Boolean {
        return sharedPreferences.getBoolean("isLogin", false)
    }
    //if user logout this function delete shared preferences data
    fun removeData(){
        editor.clear()
        editor.commit()
    }




}