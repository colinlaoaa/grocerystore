package com.liao.grocerystore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterRecycerCart
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.model.CartContent
import kotlinx.android.synthetic.main.activity_cart_content.*
import kotlinx.android.synthetic.main.app_bar.*

class CartContentActivity : AppCompatActivity() {
    var dbHelper = DBHelper(this)
    lateinit var mList: List<CartContent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_content)

        var toolbar = toolbar
        toolbar.title = "Shopping Cart"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }




    private fun init() {
        mList = dbHelper.readCartContent()
        recycler_view_3.layoutManager = LinearLayoutManager(this)
        var adapterRecycerCart = AdapterRecycerCart(this, mList)
        recycler_view_3.adapter = adapterRecycerCart

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item);
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }

            R.id.cart_menu ->{
                startActivity(Intent(this,CartContentActivity::class.java))
            }
        }
        return true;
    }
}