package com.liao.grocerystore.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterMyFragment
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.fragment.ProductFragment
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.Category
import com.liao.grocerystore.model.CategoryContent
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*


class SubCategoryActivity : AppCompatActivity(), ProductFragment.FragmentListener {

    private var textViewCount: TextView? = null
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)


        toolbar("Sub Category")
        dbHelper = DBHelper(this)

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
                adapterMyFragment.setupListener(this)

            },
            Response.ErrorListener {

            })

        requestQueue.add(request)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)

        var item = menu.findItem(R.id.cart_menu)
        MenuItemCompat.setActionView(item, R.layout.menu_cart_layout)
        var view = MenuItemCompat.getActionView(item)
        textViewCount = view.text_view_cart_count
        updateCartCount()
        view.setOnClickListener {
            startActivity(Intent(this, CartContentActivity::class.java))
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        updateCartCount()
    }

    private fun updateCartCount() {
        var count = dbHelper.readCartContentQuantity()
        if (count == 0)
            textViewCount?.visibility = View.GONE
        else {
            textViewCount?.visibility = View.VISIBLE
            textViewCount?.text = count.toString()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item);
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

        }
        return true;
    }

    override fun buttonHasClicked() {
        updateCartCount()
    }


}




