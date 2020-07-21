package com.liao.grocerystore.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterRecyclerCategory
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.Category
import com.liao.grocerystore.model.CategoryResponse
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.new_row.view.*

class CategoryActivity : AppCompatActivity(),AdapterRecyclerCategory.OnAdapterInteraction {


    var mList: ArrayList<Category> = ArrayList()
    lateinit var adapterRecyclerCategory: AdapterRecyclerCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

//        var toolbar = toolbar
//        toolbar.title = "Grocery"
//        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar("Grocery")
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        image_view_1.setImageResource(R.drawable.pic1)


        getdata()


        recycler_view_1.layoutManager = GridLayoutManager(this, 2)
        adapterRecyclerCategory = AdapterRecyclerCategory(this, mList)
        adapterRecyclerCategory.setAdapterListener(this)
        recycler_view_1.adapter = adapterRecyclerCategory


    }

    private fun getdata() {
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, Endpoints.getCategory(), Response.Listener {

            var gson = GsonBuilder().create()
            var newResponse = gson.fromJson(it, CategoryResponse::class.java)

            adapterRecyclerCategory.setdata(newResponse.data)


        }, Response.ErrorListener {

        })

        requestQueue.add(request)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bar, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item);
        when (item.itemId) {
//            android.R.id.home -> {
//                finish()
//            }

            R.id.cart_menu -> {
                startActivity(Intent(this, CartContentActivity::class.java))
            }
        }
        return true;
    }

    override fun onItemClicked(itemView: View, category: Category) {
        itemView.view_1.setOnClickListener {
            var myIntent = Intent(this, SubCategoryActivity::class.java)
            myIntent.putExtra(Category.CATID, category.catId)
            startActivity(myIntent)
        }
    }
}