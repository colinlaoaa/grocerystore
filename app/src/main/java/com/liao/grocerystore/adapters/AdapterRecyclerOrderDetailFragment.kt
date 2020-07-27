package com.liao.grocerystore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liao.grocerystore.R
import com.liao.grocerystore.model.Product
import kotlinx.android.synthetic.main.new_row_order_history.view.*

class AdapterRecyclerOrderDetailFragment(var mContext: Context, var mList: List<Product>) :
    RecyclerView.Adapter<AdapterRecyclerOrderDetailFragment.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.new_row_order_history, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(product: Product) {
            itemView.text_view_product.text = product.productName
            itemView.text_view_product_number.text = product.quantity.toString()

        }
    }
}