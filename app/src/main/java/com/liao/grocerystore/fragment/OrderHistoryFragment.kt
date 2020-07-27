package com.liao.grocerystore.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.liao.grocerystore.R
import com.liao.grocerystore.adapters.AdapterRecyclerOrderDetailFragment
import com.liao.grocerystore.model.Order
import kotlinx.android.synthetic.main.activity_cart_content.view.*
import kotlinx.android.synthetic.main.fragment_order_history.view.*
import kotlinx.android.synthetic.main.fragment_order_history.view.text_view_delivery_number
import kotlinx.android.synthetic.main.fragment_order_history.view.text_view_subtotal_number
import kotlinx.android.synthetic.main.activity_cart_content.view.text_view_total_number as text_view_total_number1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderHistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Order? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Order
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_order_history, container, false)
        init(view)
        view.recycler_view_order_detail.layoutManager = GridLayoutManager(activity,2)
        var adapterRecyclerOrderDetailFragment = AdapterRecyclerOrderDetailFragment(context!!,
            param1!!.products)
        view.recycler_view_order_detail.adapter = adapterRecyclerOrderDetailFragment


        return view
    }

    private fun init(view: View) {
        view.text_view_subtotal_number.text = param1?.orderSummary?.orderAmount.toString()
        view.text_view_delivery_number.text = param1?.orderSummary?.deliveryCharges.toString()
        view.text_view_saving_number.text = param1?.orderSummary?.discount.toString()
        view.text_view_total_number2.text = param1?.orderSummary?.totalAmount.toString()



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderHistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Order, param2: String) =
            OrderHistoryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}