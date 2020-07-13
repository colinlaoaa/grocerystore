package com.liao.grocerystore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterRecycler
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.model.Category
import com.liao.grocerystore.model.CategoryResponse
import kotlinx.android.synthetic.main.activity_main.*

class CategoryActivity : AppCompatActivity() {

    var mList: ArrayList<Category> = arrayListOf()
    lateinit var adapterRecycler: AdapterRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_view_1.setImageResource(R.drawable.ic_android_black_24dp)

        getdata()


        recycler_view_1.layoutManager = GridLayoutManager(this, 2)
        adapterRecycler = AdapterRecycler(this, mList)
        recycler_view_1.adapter = adapterRecycler



    }

    private fun getdata()  {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, Endpoints.getCategory(), Response.Listener {

            var gson = GsonBuilder().create()
            var newResponse = gson.fromJson(it, CategoryResponse::class.java)

            adapterRecycler.setdata(newResponse.data)


        }, Response.ErrorListener {

        })

        requestQueue.add(request)



    }
}