package com.liao.grocerystore.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterAddress
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.helper.toolbar
import com.liao.grocerystore.model.Address
import com.liao.grocerystore.model.AddressGet
import com.liao.myapplication.helper.SessionManager
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_cart_content.*
import kotlinx.android.synthetic.main.new_row_address.view.*

class AddressActivity : AppCompatActivity(), AdapterAddress.OnAdapterInteraction {
    private lateinit var sessionManager: SessionManager
    private var mList: List<Address> = ArrayList()
    lateinit var adapterAddress: AdapterAddress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        toolbar("Address")

        getAddress()
        init()


    }

    private fun getAddress() {
        sessionManager = SessionManager(this)
        var _id = sessionManager.getUserId()

        var request = StringRequest(
            Request.Method.GET, Endpoints.readAddress(_id),
            Response.Listener {
                var gson = GsonBuilder().create()
                var address = gson.fromJson(it.toString(), AddressGet::class.java)
                if (!address.error) {
                    mList = address.data
                    adapterAddress.setList(mList)
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "not success", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            })


        Volley.newRequestQueue(this).add(request)
    }

    private fun deleteAddress(address: Address, position: Int) {

        var request = StringRequest(
            Request.Method.DELETE, Endpoints.readAddress(address._id),
            Response.Listener {
                mList -= mList[position]
                adapterAddress.setList(mList)
                adapterAddress.notifyItemRemoved(position)
                adapterAddress.notifyItemChanged(position, mList.size)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            })


        Volley.newRequestQueue(this).add(request)
    }

    private fun init() {
        recycler_view_address.layoutManager = LinearLayoutManager(this)
        adapterAddress = AdapterAddress(this, mList)
        recycler_view_address.adapter = adapterAddress
        adapterAddress.setAdapterListener(this)

        recycler_view_address.itemAnimator = DefaultItemAnimator()
        recycler_view_address.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )


        button_add_address.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun itemClicked(
        itemView: View,
        address: Address,
        position: Int
    ) {
        itemView.recycler_view_address_row.setOnClickListener {
            var myIntent = Intent(this, PaymentActivity::class.java)
            myIntent.putExtra(Address.KEY, address)
            startActivity(myIntent)
        }

        itemView.button_delete.setOnClickListener {
            deleteAddress(address, position)
        }

        itemView.button_edit.setOnClickListener {
            var myIntent = Intent(this, AlterAddressActivity::class.java)
            myIntent.putExtra(Address.KEY, address)
            startActivity(myIntent)
        }
    }
}