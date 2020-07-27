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
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.*
import com.liao.myapplication.helper.SessionManager
import kotlinx.android.synthetic.main.activity_cart_content.*
import kotlinx.android.synthetic.main.activity_order_confirmation.*
import org.json.JSONObject
import java.text.DecimalFormat

class OrderConfirmationActivity : AppCompatActivity() {
    private val KEY_PAYMENT_METHOD = "payment_method"
    private val NAME = "NAME"
    private val MOBILE = "MOBILE"
    lateinit var dbHelper: DBHelper
    lateinit var address: Address
    lateinit var paymentMode: String
    lateinit var userId: String
    lateinit var sessionManager: SessionManager
    lateinit var name:String
    lateinit var mobile:String
    var mList: ArrayList<CartContent> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)

        toolbar("Order Complete")
        this.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        init()

        sendOrder()
    }


    private fun sendOrder() {
        dbHelper = DBHelper(this)
        mList = dbHelper.readCartContent()
        sessionManager = SessionManager(this)
        userId = sessionManager.getUserId().toString()
        var params = HashMap<String, Any>()
        var product: ArrayList<Any> = ArrayList()
        var singleProduct = HashMap<String, Any>()
        params["userId"] = userId


        //Object com.liao.grocerystore.model.User
        var user = HashMap<String, Any?>()
        user["email"] = sessionManager.getLoginEmail()
        user["mobile"] = mobile
        user["name"] = name
        var jsonObjectUser = JSONObject(user as Map<*,*>)
        params["user"] = jsonObjectUser


        //List of Object com.liao.grocerystore.model.Product
        for (item in mList) {
            singleProduct["image"] = item.image
            singleProduct["mrp"] = item.mrp.toInt()
            singleProduct["price"] = item.price.toInt()
            singleProduct["quantity"] = item.quantity
            singleProduct["productName"] = item.productName
            val jsonObjectProduct = JSONObject(singleProduct as Map<*, *>)
            product.add(jsonObjectProduct)
        }
        params["products"] = product

        //Object PaymentMethod
        var paymentMo = HashMap<String, String>()
        paymentMo["paymentMode"] = paymentMode
        var jsonObjectPayment = JSONObject(paymentMo as Map<*, *>)
        params["payment"] = jsonObjectPayment

        //Object com.liao.grocerystore.model.ShippingAddress
        var shippingaddress = HashMap<String, Any>()
        shippingaddress["pincode"] = address.pincode
        shippingaddress["houseNo"] = address.houseNo
        shippingaddress["streetName"] = address.streetName
        shippingaddress["city"] = address.city
        val jsonObjectAddress = JSONObject(shippingaddress as Map<*, *>)
        params["shippingAddress"] = jsonObjectAddress


        //Object com.liao.grocerystore.model.OrderSummary
        var orderSummary = HashMap<String, Any>()
        var res = dbHelper.checkoutTotal()
        val df = DecimalFormat("#.00")
        orderSummary["totalAmount"] = df.format(res[3]+res[2]).toDouble()
        orderSummary["ourPrice"] = df.format(res[0]+res[2]+res[4]).toDouble()
        orderSummary["orderAmount"] = df.format(res[0]+res[4]+res[2]).toDouble()
        orderSummary["discount"] = df.format(res[1]).toDouble()
        orderSummary["deliveryCharges"] = df.format(res[4]).toDouble()
        val jsonObjectOrderSummary = JSONObject(orderSummary as Map<*,*>)
        params["orderSummary"] = jsonObjectOrderSummary


        //Entire Object
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
        mobile = i.getStringExtra(MOBILE).toString()
        name = i.getStringExtra(NAME).toString()

        button_back_category.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }


    }
}