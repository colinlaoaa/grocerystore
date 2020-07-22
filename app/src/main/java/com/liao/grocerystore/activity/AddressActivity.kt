package com.liao.grocerystore.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterAddress
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.model.Address
import com.liao.grocerystore.model.AddressGet
import com.liao.myapplication.helper.SessionManager
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_cart_content.*

class AddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    private var mList: List<Address> = ArrayList()
    lateinit var adapterAddress: AdapterAddress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        getAddress()
        init()


    }

    private fun getAddress() {
        sessionManager = SessionManager(this)
        var _id = sessionManager.getUserId()

        var request = StringRequest(
            Request.Method.GET, Endpoints.readAddress(_id),
            Response.Listener {
                var gson = GsonBuilder().create()
                var address = gson.fromJson(it.toString(), AddressGet::class.java)
                if (!address.error) {
                    mList = address.data
                    adapterAddress.setList(mList!!)
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                }else{Toast.makeText(applicationContext, "not success", Toast.LENGTH_SHORT).show()}
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            })


        Volley.newRequestQueue(this).add(request)
    }

    private fun init() {
        recycler_view_address.layoutManager = LinearLayoutManager(this)
        adapterAddress = AdapterAddress(this,mList)
        recycler_view_address.adapter = adapterAddress

        recycler_view_address.itemAnimator = DefaultItemAnimator()
        recycler_view_address.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )


        button_add_address.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }


    }
}