package com.liao.grocerystore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterMyFragment
import com.liao.grocerystore.adapters.AdapterRecyclerFragment
import com.liao.grocerystore.app.Endpoints
import com.liao.grocerystore.model.CategoryContent
import com.liao.grocerystore.model.Data
import com.liao.grocerystore.model.ProductContent
import com.liao.grocerystore.model.ProductData
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.fragment_category.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"



/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {


    var mList: ArrayList<ProductData> = ArrayList()

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
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_category, container, false)

        init(view)

        return view
    }

    private fun init(view: View) {

        getdata(param1!!.toInt(),view)





    }

    private fun getdata(subId: Int,view: View) {

        var requestQueue = Volley.newRequestQueue(context)
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductsBySubId(subId),
            Response.Listener {

                var gson = GsonBuilder().create()
                var newResponse = gson.fromJson(it, ProductContent::class.java)
                mList= newResponse.data

                view.recycler_view_2.layoutManager = LinearLayoutManager(context)
                var adapterRecyclerFragment = AdapterRecyclerFragment(activity!!, mList)
                adapterRecyclerFragment.setdata(mList)
                view.recycler_view_2.adapter = adapterRecyclerFragment




            },
            Response.ErrorListener {

            })

        requestQueue.add(request)


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
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)


                }
            }
    }
}