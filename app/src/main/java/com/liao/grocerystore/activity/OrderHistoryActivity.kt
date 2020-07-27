package com.liao.grocerystore.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterRecyclerOrder
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.fragment.OrderHistoryFragment
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.Order
import com.liao.grocerystore.model.OrderGet
import com.liao.myapplication.helper.SessionManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.new_row_fragment.view.*

class OrderHistoryActivity : AppCompatActivity(),
    AdapterRecyclerOrder.OnAdapterInteractionOrderHistory {
    var flag = true
    lateinit var sessionManager: SessionManager
    private var orderHistoryList: List<Order> = ArrayList()
    lateinit var adapterRecyclerOrder: AdapterRecyclerOrder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        toolbar("Order History")

        sessionManager = SessionManager(this)
        getOrderHistory(sessionManager.getUserId())
        init()


    }

    private fun init() {
        adapterRecyclerOrder = AdapterRecyclerOrder(this, orderHistoryList)
        adapterRecyclerOrder.setAdapterListener(this)
        recycler_view_order_history.layoutManager = LinearLayoutManager(this)
        recycler_view_order_history.adapter = adapterRecyclerOrder

    }


    private fun getOrderHistory(userId: String?) {
        var requestQueue = Volley.newRequestQueue(this)
        var request =
            StringRequest(
                Request.Method.GET,
                Endpoints.sendOrders() + "/" + userId,
                Response.Listener {

                    var gson = GsonBuilder().create()
                    var newResponse = gson.fromJson(it, OrderGet::class.java)
                    if (newResponse.error) {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    } else {
                        orderHistoryList = newResponse.data
                        adapterRecyclerOrder.setdata(orderHistoryList)

                    }

                },
                Response.ErrorListener {

                })

        requestQueue.add(request)

    }

    override fun onItemClicked(itemView: View, order: Order, mList: List<Order>, position: Int) {
        itemView.text_view_3.text = order.date.substring(0, 10)
        itemView.text_view_2.text = order.payment.paymentMode
        itemView.button_1.visibility = View.GONE
        itemView.button_2.visibility = View.GONE
        itemView.button_3.visibility = View.GONE
        Picasso.get().load(Config.IMAGE_BASE_URL + order.products[0].image)
            .placeholder(R.drawable.noimage)
            .error(R.drawable.noimage)
            .into(itemView.image_view_1)

        var totalQty: Int = 0
        for (singleProduct in order.products) {
            totalQty += singleProduct.quantity
        }
        itemView.text_view_5.text = "Total Item: " + totalQty

        itemView.text_view_1.text = "total amount: " + order.orderSummary.totalAmount.toString()

        itemView.view_2.setOnClickListener {

            var orderHistoryFragment = OrderHistoryFragment.newInstance(order,"")
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, orderHistoryFragment).commit()
            flag = if (flag) {
                false
            } else {
                supportFragmentManager.beginTransaction()
                    .remove(orderHistoryFragment).commit()
                true
            }

        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

}