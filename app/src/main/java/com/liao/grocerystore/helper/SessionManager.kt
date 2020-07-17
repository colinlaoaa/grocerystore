package com.liao.myapplication.helper

import android.content.Context

class SessionManager(mContext: Context) {
    var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()


    companion object {
        const val FILE_NAME = "my_file"
        const val KEY_NAME = "firstName"
        const val KEY_LOG_EMAIL = "log_email"
        const val KEY_REG_EMAIL = "reg_email"
        const val KEY_MOBILE = "mobile"
        const val KEY_TOKEN = "token"
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }


    fun register(firstName: String, email: String, mobile: String) {
        editor.putString(KEY_NAME, firstName)
        editor.putString(KEY_REG_EMAIL, email)
        editor.putString(KEY_MOBILE, mobile)
        editor.commit()
    }


    fun login(email: String, token: String) {
        editor.putString(KEY_LOG_EMAIL, email)
        editor.putString(KEY_TOKEN, token)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.commit()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logout() {
        editor.clear()
        editor.commit()

    }


}