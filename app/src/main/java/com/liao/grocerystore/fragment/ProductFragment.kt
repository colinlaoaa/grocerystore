package com.liao.grocerystore.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.activity.ProductDetailActivity
import com.liao.grocerystore.adapters.AdapterRecyclerFragment
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.model.CartContent
import com.liao.grocerystore.model.ProductContent
import com.liao.grocerystore.model.ProductData
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_category.view.*
import kotlinx.android.synthetic.main.new_row_fragment.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment(), AdapterRecyclerFragment.OnAdapterInteraction {
    lateinit var adapterRecyclerFragment: AdapterRecyclerFragment
    lateinit var dbHelper: DBHelper
    var listener : FragmentListener? = null

    private var mList: ArrayList<ProductData> = ArrayList()

    // TODO: Rename and change types of parameters
    private var param1: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_category, container, false)

        dbHelper = DBHelper(context!!)

        init(view)

        return view
    }

    private fun init(view: View) {

        getdata(param1!!.toString().toInt())

        view.recycler_view_2.layoutManager = LinearLayoutManager(activity)
        adapterRecyclerFragment = AdapterRecyclerFragment(activity!!, mList)
        adapterRecyclerFragment.setAdapterListener(this)

        view.recycler_view_2.adapter = adapterRecyclerFragment

        view.recycler_view_2.itemAnimator = DefaultItemAnimator()
        view.recycler_view_2.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )


    }

    private fun getdata(subId: Int) {

        var requestQueue = Volley.newRequestQueue(context)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductsBySubId(subId),
            Response.Listener {

                var gson = GsonBuilder().create()
                var newResponse = gson.fromJson(it, ProductContent::class.java)
                mList = newResponse.data
                adapterRecyclerFragment.setdata(mList)


            },
            Response.ErrorListener {

            })

        requestQueue.add(request)


    }

    override fun onResume() {
        super.onResume()
        getdata(param1.toString().toInt())
    }


    interface FragmentListener{
        fun buttonHasClicked()
    }
    fun setFragmentListener(fragmentListener: FragmentListener){
        listener = fragmentListener
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)


                }
            }
    }


    override fun onItemClicked(itemView: View, productData: ProductData) {

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
        itemView.text_view_5.text = quantity.toString()

        if (itemView.text_view_5.text != "0") {
            itemView.button_1.visibility = View.GONE
            itemView.button_2.visibility = View.VISIBLE
            itemView.button_3.visibility = View.VISIBLE
        } else {
            itemView.button_1.visibility = View.VISIBLE
            itemView.button_2.visibility = View.GONE
            itemView.button_3.visibility = View.GONE
        }

        itemView.button_1.setOnClickListener {
            itemView.button_1.visibility = View.GONE

            itemView.button_2.visibility = View.VISIBLE
            itemView.button_3.visibility = View.VISIBLE
            itemView.text_view_5.visibility = View.VISIBLE


            Toast.makeText(activity, "added to cart", Toast.LENGTH_SHORT).show()

            if (dbHelper.addquantity(cartContent._id)) {
                cartContent.quantity += 1
                dbHelper.updateCartContent(cartContent)
                itemView.text_view_5.text = "${cartContent.quantity}"
            } else {
                cartContent.quantity += 1
                dbHelper.addCartContent(cartContent)
                itemView.text_view_5.text = "${cartContent.quantity}"

            }

            listener?.buttonHasClicked()
        }

        itemView.button_2.setOnClickListener {


            if (itemView.text_view_5.text == "1") {
                cartContent.quantity -= 1
                dbHelper.deleteCartContent(cartContent._id)
                itemView.button_2.visibility = View.GONE
                itemView.button_3.visibility = View.GONE
                itemView.text_view_5.visibility = View.GONE
                itemView.button_1.visibility = View.VISIBLE
            } else {
                cartContent.quantity -= 1
                itemView.text_view_5.text =
                    "${cartContent.quantity}"
                dbHelper.updateCartContent(cartContent)

            }
            listener?.buttonHasClicked()
        }

        itemView.button_3.setOnClickListener {

            if (!dbHelper.addquantity(cartContent._id)) {
                cartContent.quantity += 1
                dbHelper.addCartContent(cartContent)
            }

            if (dbHelper.addquantity(cartContent._id) && itemView.text_view_5.text.toString()
                    .toInt() < productData.quantity
            ) {
                cartContent.quantity += 1
                dbHelper.updateCartContent(cartContent)
                itemView.text_view_5.text =
                    "${cartContent.quantity}"
                if ((productData.quantity - cartContent.quantity) % 5 == 0) {
                    Toast.makeText(
                        activity,
                        "${productData.quantity - cartContent.quantity} left",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                itemView.text_view_5.text =
                    "${cartContent.quantity}"
                Toast.makeText(
                    activity,
                    "only have ${productData.quantity} in stock",
                    Toast.LENGTH_SHORT
                ).show()
            }
            listener?.buttonHasClicked()

        }

        itemView.view_2.setOnClickListener {
            var myIntent = Intent(activity, ProductDetailActivity::class.java)
            myIntent.putExtra(ProductData.PRODUCT_DETAIL, productData)
            startActivity(myIntent)
        }
    }
}