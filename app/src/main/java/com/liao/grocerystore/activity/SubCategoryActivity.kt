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
import com.liao.grocerystore.adapters.AdapterRecyclerFragment
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.model.CategoryContent
import com.liao.grocerystore.model.Data
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {

    var mList: ArrayList<Data> = arrayListOf()
    var subname : ArrayList<String> = arrayListOf()
//    lateinit var adapterRecyclerFragment: AdapterRecyclerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        var i = intent
        var count = i.getIntExtra("COUNT", 1)
        var catName = i.getStringArrayListExtra("CATNAME")
        println(count)
        println(catName?.get(0))
        println(catName?.get(1))

        for (num in 1..count) {
            getdata(num)


            var adapter = AdapterMyFragment(supportFragmentManager)
            adapter.addFragment(subname)
            view_pager.adapter = adapter
            tab_layout_1.setupWithViewPager(view_pager)
        }


//            recycler_view_2.layoutManager = LinearLayoutManager(this)
//             adapterRecyclerFragment= AdapterRecyclerFragment(this, mList)
//            recycler_view_2.adapter = adapterRecyclerFragment

    }

    private fun getdata(num: Int) {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryByCatId(num),
            Response.Listener {

                var gson = GsonBuilder().create()
                var newResponse = gson.fromJson(it, CategoryContent::class.java)
                var adapterRecyclerFragment = AdapterRecyclerFragment(this, mList)
                adapterRecyclerFragment.setdata(newResponse.data)
                for(i in 0..(newResponse.data.size-1)) {
                    subname.add(newResponse.data[i].subName)
                }

            },
            Response.ErrorListener {

            })

        requestQueue.add(request)

    }
}
