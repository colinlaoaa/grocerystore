package com.liao.grocerystore.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.model.CartContent
import com.liao.grocerystore.model.ProductData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*

class ProductDetailActivity : AppCompatActivity() {

    lateinit var dbHelper:DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        dbHelper = DBHelper(this)



        var i =intent
        var productData = i.getSerializableExtra(ProductData.PRODUCT_DETAIL) as ProductData

        Picasso.get().load(Config.IMAGE_BASE_URL + productData.image).placeholder(R.drawable.noimage)
            .error(R.drawable.noimage)
            .into(image_view_3)

        text_view_1.text = "mrp: "+productData.mrp.toString()
        text_view_1.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        text_view_2.text = "price: "+productData.price.toString()
        text_view_3.text = productData.productName
        text_view_4.text = productData.description


        button_add_cart.setOnClickListener {
            var cartContent = CartContent(productData._id,
                productData.productName,
                productData.price,
                productData.mrp,
                productData.image,
                productData.quantity)
            dbHelper.addCartContent(cartContent)

        }




        var toolbar = toolbar
        toolbar.title = "${productData.productName}"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bar, menu)

        return true
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