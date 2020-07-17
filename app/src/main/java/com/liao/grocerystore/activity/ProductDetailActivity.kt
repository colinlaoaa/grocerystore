package com.liao.grocerystore.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
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
import kotlinx.android.synthetic.main.new_row_fragment.view.*

class ProductDetailActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        dbHelper = DBHelper(this)


        var i = intent
        var productData = i.getSerializableExtra(ProductData.PRODUCT_DETAIL) as ProductData

        Picasso.get().load(Config.IMAGE_BASE_URL + productData.image)
            .placeholder(R.drawable.noimage)
            .error(R.drawable.noimage)
            .into(image_view_3)

        text_view_1.text = "mrp: " + productData.mrp.toString()
        text_view_1.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        text_view_2.text = "price: " + productData.price.toString()
        text_view_3.text = productData.productName
        text_view_4.text = productData.description


        var cartContent = CartContent(
            productData._id,
            productData.productName,
            productData.price,
            productData.mrp,
            productData.image,
            0
        )
        var quantity = dbHelper.readCartContentItemQuantity(cartContent._id)
        cartContent.quantity = quantity
        text_view_quantity.text = quantity.toString()

        button_buy.setOnClickListener {

            if (dbHelper.addquantity(cartContent._id)) {
                cartContent.quantity += 1
                dbHelper.updateCartContent(cartContent)

            } else {
                cartContent.quantity += 1
                dbHelper.addCartContent(cartContent)
            }
            Toast.makeText(applicationContext, "ADDED", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, CartContentActivity::class.java))

        }


        if (text_view_quantity.text != "0") {
            button_add_cart.visibility = View.GONE
            button_add.visibility = View.VISIBLE
            button_minus.visibility = View.VISIBLE
        } else {
            button_add_cart.visibility = View.VISIBLE
            button_add.visibility = View.GONE
            button_minus.visibility = View.GONE
        }



        button_add_cart.setOnClickListener {
            button_add_cart.visibility = View.GONE
            button_add.visibility = View.VISIBLE
            button_minus.visibility = View.VISIBLE
            if (dbHelper.addquantity(cartContent._id)) {
                cartContent.quantity += 1
                dbHelper.updateCartContent(cartContent)

            } else {
                cartContent.quantity += 1
                dbHelper.addCartContent(cartContent)
            }
            Toast.makeText(applicationContext, "ADDED", Toast.LENGTH_SHORT).show()
            text_view_quantity.text = cartContent.quantity.toString()

        }


        button_minus.setOnClickListener {
            if (text_view_quantity.text == "1") {
                cartContent.quantity -= 1
                dbHelper.deleteCartContent(cartContent._id)
                button_minus.visibility = View.GONE
                button_add.visibility = View.GONE
                button_add_cart.visibility = View.VISIBLE
            } else {
                cartContent.quantity -= 1
                text_view_quantity.text =
                    "${cartContent.quantity}"
                dbHelper.updateCartContent(cartContent)

            }
        }

        button_add.setOnClickListener {

            if (dbHelper.addquantity(cartContent._id)) {
                cartContent.quantity += 1
                dbHelper.updateCartContent(cartContent)
            } else {
                cartContent.quantity = text_view_quantity.text.toString().toInt()
                cartContent.quantity += 1
                dbHelper.addCartContent(cartContent)
            }

            if (text_view_quantity.text.toString()
                    .toInt() <= productData.quantity.toInt()
            ) {
                text_view_quantity.text =
                    "${cartContent.quantity}"
                Toast.makeText(
                    this,
                    "${productData.quantity.toInt() - text_view_quantity.text.toString()
                        .toInt()} left",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(
                    this,
                    "only have ${productData.quantity} in stock",
                    Toast.LENGTH_SHORT
                ).show()
            }

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

    override fun onResume() {

        super.onResume()
    }
}