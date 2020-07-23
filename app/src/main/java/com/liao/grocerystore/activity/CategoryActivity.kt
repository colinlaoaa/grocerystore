package com.liao.grocerystore.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterRecyclerCategory
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.Category
import com.liao.grocerystore.model.CategoryResponse
import com.liao.myapplication.helper.SessionManager
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.new_row.view.*

class CategoryActivity : AppCompatActivity(), AdapterRecyclerCategory.OnAdapterInteraction,
    NavigationView.OnNavigationItemSelectedListener {


    private var mList: ArrayList<Category> = ArrayList()
    private lateinit var adapterRecyclerCategory: AdapterRecyclerCategory
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var sessionManager: SessionManager
    var textViewCount: TextView? = null
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        dbHelper = DBHelper(this)
        toolbar("Grocery")
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        image_view_1.setImageResource(R.drawable.pic1)

        drawerLayout = drawer_layout
        navView = nav_view
        var headView = navView.getHeaderView(0)
        sessionManager = SessionManager(this)
        if (sessionManager.getUserName() == null || sessionManager.getUserName() == "") {
            headView.text_view_user_name.text = sessionManager.getLoginEmail()
        } else {
            headView.text_view_user_name.text =sessionManager.getUserName()
        }
        val toogle = ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0)
        toogle.syncState()
        navView.setNavigationItemSelectedListener(this)


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

            progress_bar.visibility = View.GONE
            adapterRecyclerCategory.setdata(newResponse.data)


        }, Response.ErrorListener {

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


    override fun onItemClicked(itemView: View, category: Category) {
        itemView.view_1.setOnClickListener {
            var myIntent = Intent(this, SubCategoryActivity::class.java)
            myIntent.putExtra(Category.CATID, category.catId)
            startActivity(myIntent)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
        return true
    }
}