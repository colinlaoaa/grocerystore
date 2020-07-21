package com.liao.grocerystore.activity;

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.app.Endpoints
import com.liao.myapplication.helper.SessionManager
import com.liao.myapplication.model.Data
import com.liao.myapplication.model.LoginFailResponse
import com.liao.myapplication.model.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        sessionManager = SessionManager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        init()

        button_login.setOnClickListener {
            login()

        }

    }

    private fun login() {

        var email = edit_text_1.text.toString()
        var password = edit_text_2.text.toString()


        var params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password

        val jsonObject = JSONObject(params as Map<*, *>)


        var request = JsonObjectRequest(
            Request.Method.POST, Endpoints.getLogin(), jsonObject,
            Response.Listener {
                var gson = GsonBuilder().create()
                var login = gson.fromJson(it.toString(), LoginFailResponse::class.java)
                var loginsuccess = gson.fromJson(it.toString(), LoginResponse::class.java)
                if (!login.error) {
                    sessionManager.login(email, loginsuccess.token,loginsuccess.user._id)
                    startActivity(Intent(this, CategoryActivity::class.java))


                } else {
                    Toast.makeText(applicationContext, login.message, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            })


        Volley.newRequestQueue(this).add(request)


    }

    private fun init() {
        var toolbar = toolbar
        toolbar.title = "Login"
        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        text_view_1.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        text_view_1.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        var i = intent
        edit_text_1.setText(i.getStringExtra(Data.EMAIL_KEY))


    }
}