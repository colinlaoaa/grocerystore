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
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.Address
import kotlinx.android.synthetic.main.activity_add_address.*
import org.json.JSONObject

class AlterAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        toolbar("Alter Address")


        init()
    }

    private fun init() {
        var i = intent
        var address = i.getSerializableExtra(Address.KEY) as Address
        edit_text_city.setText(address.city)
        edit_text_houseNo.setText(address.houseNo)
        edit_text_pincode.setText(address.pincode.toString())
        edit_text_streetName.setText(address.streetName)
        edit_text_type.setText(address.type)

        button_save_address.setOnClickListener {
            address.city = edit_text_city.text.toString()
            address.houseNo = edit_text_houseNo.text.toString()
            address.streetName = edit_text_streetName.text.toString()
            address.type = edit_text_type.text.toString()
            address.pincode = edit_text_pincode.text.toString().toInt()
            updateAddress(address)
        }
    }

    private fun updateAddress(address: Address) {
        var params = HashMap<String, Any?>()
        params["city"] = address.city
        params["houseNo"] = address.houseNo
        params["pincode"] = address.pincode
        params["streetName"] = address.streetName
        params["type"] = address.type
        params["userId"] = address.userId
        val jsonObject = JSONObject(params as Map<*, *>)
        var request = JsonObjectRequest(
            Request.Method.PUT, Endpoints.readAddress(address._id),
            jsonObject,
            Response.Listener {
                startActivity(Intent(this, AddressActivity::class.java))
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            })


        Volley.newRequestQueue(this).add(request)
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