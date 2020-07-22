package com.liao.grocerystore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.model.*
import com.liao.myapplication.helper.SessionManager
import kotlinx.android.synthetic.main.activity_order_confirmation.*
import org.json.JSONObject

class OrderConfirmationActivity : AppCompatActivity() {
    private val KEY_PAYMENT_METHOD = "payment_method"
    lateinit var dbHelper: DBHelper
    lateinit var address: Address
    lateinit var paymentMode: String
    lateinit var userId: String
    lateinit var sessionManager: SessionManager
    var mList: ArrayList<CartContent> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)

        init()
        getData()
        sendOrder()
    }

    private fun sendOrder() {


    }

    private fun getData() {
        dbHelper = DBHelper(this)
        mList = dbHelper.readCartContent()
        sessionManager = SessionManager(this)
        userId = sessionManager.getUserId().toString()
        var params = HashMap<String, Any>()
        var product: ArrayList<Any> = ArrayList()
        var singleProduct = HashMap<String, Any>()
        params["userId"] = userId
        for (item in mList) {
            singleProduct["image"] = item.image
            singleProduct["mrp"] = item.mrp
            singleProduct["price"] = item.price
            singleProduct["quantity"] = item.quantity
            singleProduct["productName"] = item.productName
            val jsonObjectProduct = JSONObject(singleProduct as Map<*, *>)
            product.add(jsonObjectProduct)
        }
        params["products"] = product

        var paymentMo = HashMap<String, String>()
        paymentMo["paymentMode"] = paymentMode
        var jsonObjectPayment = JSONObject(paymentMo as Map<*, *>)
        params["payment"] = jsonObjectPayment


        var shippingaddress = HashMap<String, Any>()
        shippingaddress["pincode"] = address.pincode
        shippingaddress["houseNo"] = address.houseNo
        shippingaddress["streetName"] = address.streetName
        shippingaddress["city"] = address.city
        val jsonObjectAddress = JSONObject(shippingaddress as Map<*, *>)
        params["shippingAddress"] = jsonObjectAddress

        val jsonObject = JSONObject(params as Map<*, *>)

        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.POST, Endpoints.sendOrders(),
            jsonObject,
            Response.Listener {

                var gson = GsonBuilder().create()
                var addressUploadRespone = gson.fromJson(it.toString(), OrderPost::class.java)
                if (addressUploadRespone.error) {
                    Toast.makeText(
                        this,
                        "error occur",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "successfully sent your order to the server",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(request)

    }


    private fun init() {
        var i = intent
        address = i.getSerializableExtra(Address.KEY) as Address
        paymentMode = i.getStringExtra(KEY_PAYMENT_METHOD).toString()

        button_back_category.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        button_logout.setOnClickListener {
            sessionManager.logout()
            Toast.makeText(this, "Successfully logout", Toast.LENGTH_SHORT).show()
            dbHelper.clearCartContent()
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}