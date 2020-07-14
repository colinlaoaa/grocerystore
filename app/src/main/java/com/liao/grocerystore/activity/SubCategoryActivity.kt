package com.liao.grocerystore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterMyFragment

import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.model.Category
import com.liao.grocerystore.model.CategoryContent


import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        var i = intent
        var catId = i.getIntExtra(Category.CATID, 1)

        getdata(catId)


    }

    private fun getdata(catId: Int) {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatId(catId),
            Response.Listener {

                var gson = GsonBuilder().create()
                var newResponse = gson.fromJson(it, CategoryContent::class.java)

                var adapterMyFragment = AdapterMyFragment(supportFragmentManager)
                adapterMyFragment.addFragment(newResponse.data)

                view_pager.adapter = adapterMyFragment
                tab_layout_1.setupWithViewPager(view_pager)

            },
            Response.ErrorListener {

            })

        requestQueue.add(request)


    }


}
