package com.liao.grocerystore.activity;

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
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
import com.liao.myapplication.model.RegisterResponse
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {


        sessionManager = SessionManager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        init()

        button_register.setOnClickListener {
            register()


        }
    }

    private fun init() {
        var toolbar = toolbar
        toolbar.title = "Register"
        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        text_view_2.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        text_view_2.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }


    private fun register() {
        var firstName = edit_text_3.text.toString()
        var email = edit_text_4.text.toString()
        var password = edit_text_5.text.toString()
        var mobile = edit_text_6.text.toString()


        var params = HashMap<String, String>()
        params["firstName"] = firstName
        params["email"] = email
        params["password"] = password
        params["mobile"] = mobile

        val jsonObject = JSONObject(params as Map<*, *>)


        var request = JsonObjectRequest(
            Request.Method.POST, Endpoints.getRegister(), jsonObject,
            Response.Listener {
                var gson = GsonBuilder().create()
                var register = gson.fromJson(it.toString(), RegisterResponse::class.java)
                if (register.error) {
                    Toast.makeText(applicationContext, register.message, Toast.LENGTH_SHORT).show()
                } else {


                    email = register.data.email
                    sessionManager.register(
                        register.data.firstName,
                        register.data.email,
                        register.data.mobile
                    )
                    Toast.makeText(applicationContext, register.message, Toast.LENGTH_SHORT).show()
                    var myIntent = Intent(this, LoginActivity::class.java)
                    myIntent.putExtra(Data.EMAIL_KEY, email)
                    startActivity(myIntent)
                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            })

        Volley.newRequestQueue(this).add(request)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}