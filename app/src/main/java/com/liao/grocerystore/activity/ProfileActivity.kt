package com.liao.grocerystore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.helper.toolbar
import com.liao.myapplication.helper.SessionManager
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    lateinit var dbHelper :DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        toolbar("Profile")
        dbHelper = DBHelper(this)
        sessionManager = SessionManager(this)

        init()

        button_logout.setOnClickListener {
            sessionManager.logout()
            Toast.makeText(this, "Successfully logout", Toast.LENGTH_SHORT).show()
            dbHelper.clearCartContent()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        button_update_user.setOnClickListener {
            var name = text_view_name.text.toString()
            var moblie = text_view_mobile.text.toString()
            button_update_user.visibility = View.GONE
            linear_1.visibility = View.GONE
            button_save.visibility = View.VISIBLE
            linear_2.visibility = View.VISIBLE

            edit_text_name.setText(name)
            edit_text_mobile.setText(moblie)
        }

        button_save.setOnClickListener {
            button_update_user.visibility = View.VISIBLE
            linear_1.visibility = View.VISIBLE
            button_save.visibility = View.GONE
            linear_2.visibility = View.GONE


            changeUserInfo(sessionManager.getUserId())

            text_view_name.text = sessionManager.getUserName()
            text_view_email.text = sessionManager.getLoginEmail()
            text_view_mobile.text = sessionManager.getMobile()
        }
    }


    private fun changeUserInfo(userId : String?){
        var params = HashMap<String, Any?>()
        params["firstName"] = edit_text_name.text.toString()
        params["mobile"] = edit_text_mobile.text.toString()
        //params["email"] = edit_text_email.text.toString()

        sessionManager.updateUserInfo(params["firstName"] as String,params["mobile"] as String
        )
        val jsonObject = JSONObject(params as Map<*, *>)
        var request = JsonObjectRequest(
            Request.Method.PUT, Endpoints.updateUser(userId),
            jsonObject,
            Response.Listener {
                Toast.makeText(applicationContext, "successfully changed information", Toast.LENGTH_SHORT).show()

            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            })


        Volley.newRequestQueue(this).add(request)


    }

    private fun init() {
        button_save.visibility = View.GONE
        linear_2.visibility = View.GONE
        text_view_name.text = sessionManager.getUserName()
        text_view_email.text = sessionManager.getLoginEmail()
        text_view_mobile.text = sessionManager.getMobile()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

        }
        return true
    }

}