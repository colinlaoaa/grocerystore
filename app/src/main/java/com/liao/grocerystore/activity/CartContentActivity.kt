package com.liao.grocerystore.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterRecyclerCart
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.CartContent
import kotlinx.android.synthetic.main.activity_cart_content.*
import kotlinx.android.synthetic.main.activity_cart_content.view_all
import kotlinx.android.synthetic.main.empty_cart.*
import kotlinx.android.synthetic.main.new_row_cart.view.*
import java.text.DecimalFormat

class CartContentActivity : AppCompatActivity(), AdapterRecyclerCart.OnAdapterInteraction {
    var dbHelper = DBHelper(this)
    lateinit var mList: List<CartContent>
    private lateinit var adapterRecyclerCart: AdapterRecyclerCart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_content)


        toolbar("Shopping Cart")

        init()

        checkoutTotal()
        view_all.setOnClickListener {
            checkoutTotal()
        }


        button_checkout.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }

        if (mList.isEmpty()) {
            setContentView(R.layout.empty_cart)
            button_back_category.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        CategoryActivity::class.java
                    )
                )
            }
        }

    }

    private fun checkoutTotal() {
        var res = dbHelper.checkoutTotal()
        val df = DecimalFormat("#.00")
        text_view_subtotal_number.text = df.format(res[0]).toString()
        text_view_tax_number.text = df.format(res[2]).toString()
        text_view_delivery_saving.text = df.format(res[1]).toString()
        text_view_total_number.text = df.format(res[3]).toString()
        text_view_delivery_number.text = df.format(res[4]).toString()
    }


    private fun init() {
        mList = dbHelper.readCartContent()
        recycler_view_3.layoutManager = LinearLayoutManager(this)
        adapterRecyclerCart = AdapterRecyclerCart(this, mList)
        adapterRecyclerCart.setAdapterListener(this)
        recycler_view_3.adapter = adapterRecyclerCart

        recycler_view_3.itemAnimator = DefaultItemAnimator()
        recycler_view_3.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

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

    override fun onItemClickedRemove(
        cartContent: CartContent,
        itemView: View,
        position: Int
    ) {
        itemView.button_remove.setOnClickListener {
            mList -= mList[position]
            adapterRecyclerCart.refreshList(mList)
            dbHelper.deleteCartContent(cartContent._id)
            checkoutTotal()
            adapterRecyclerCart.notifyItemRemoved(position)
            adapterRecyclerCart.notifyItemChanged(position, mList.size)
            if (mList.isEmpty()) {
                setContentView(R.layout.empty_cart)
                button_back_category.setOnClickListener {
                    startActivity(
                        Intent(
                            this,
                            CategoryActivity::class.java
                        )
                    )
                }
            }
        }
    }

    override fun onAddButtonClicked(itemView: View, cartContent: CartContent) {
        itemView.button_add.setOnClickListener {
            cartContent.quantity += 1
            dbHelper.updateCartContent(cartContent)
            itemView.text_view_4.text = cartContent.quantity.toString()
            checkoutTotal()
        }
    }

    override fun onMinusButtonClicked(itemView: View, cartContent: CartContent) {
        itemView.button_minus.setOnClickListener {

            if (itemView.text_view_4.text == "1") {
            } else {
                cartContent.quantity -= 1
                itemView.text_view_4.text =
                    "${cartContent.quantity}"
                dbHelper.updateCartContent(cartContent)
                checkoutTotal()

            }
        }
    }
}