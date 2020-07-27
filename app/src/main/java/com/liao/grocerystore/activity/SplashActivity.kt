package com.liao.grocerystore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.liao.grocerystore.R
import com.liao.myapplication.helper.SessionManager

class SplashActivity : AppCompatActivity() {

    private val delayedTime: Long = 2200
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sessionManager = SessionManager(this)

        var handler = Handler()
        handler.postDelayed({
            checkLogin()
        }, delayedTime)

    }

    private fun checkLogin(){
        var intent = if(sessionManager.isLoggedIn()){
            Intent(this, CategoryActivity::class.java)
        }else{
            Intent(this, RegisterActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}