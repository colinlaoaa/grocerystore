package com.liao.grocerystore.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.AddressPost
import com.liao.myapplication.helper.SessionManager
import kotlinx.android.synthetic.main.activity_add_address.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        toolbar("Add Address")


        saveAddress()
    }

    private fun saveAddress() {
        button_save_address.setOnClickListener {
            var city = edit_text_city.text.toString()
            var houseNo = edit_text_houseNo.text.toString()
            var pincode = edit_text_pincode.text.toString().toInt()
            var streetName = edit_text_streetName.text.toString()
            var type = edit_text_type.text.toString()

            sessionManager = SessionManager(this)

            var _id = sessionManager.getUserId()


            var params = HashMap<String, Any?>()
            params["city"] = city
            params["houseNo"] = houseNo
            params["pincode"] = pincode
            params["streetName"] = streetName
            params["type"] = type
            params["userId"] = _id


            val jsonObject = JSONObject(params as Map<*, *>)

            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.uploadAddress(),
                jsonObject,
                Response.Listener {

                    var gson = GsonBuilder().create()
                    var addressUploadRespone = gson.fromJson(it.toString(), AddressPost::class.java)
                    if (addressUploadRespone.error) {
                        Toast.makeText(
                            this,
                            "error occur, attention your input",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        startActivity(Intent(this, AddressActivity::class.java))
                    }


                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                })

            requestQueue.add(request)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            android.R.id.home ->
                finish()
        }
        return true
    }
}