package com.liao.grocerystore.activity


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.app_bar.*


class SubCategoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        //supportActionBar!!.title = "SubCategory"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        var toolbar = toolbar
        toolbar.title = "Sub Category"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bar, menu)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item);
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

            R.id.cart_menu -> {
                startActivity(Intent(this, CartContentActivity::class.java))
            }
        }
        return true;
    }


}




