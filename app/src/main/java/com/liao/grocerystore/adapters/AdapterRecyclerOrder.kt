package com.liao.grocerystore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liao.grocerystore.R
import com.liao.grocerystore.model.Order


class AdapterRecyclerOrder(var mContext: Context, var mList: List<Order>) :
    RecyclerView.Adapter<AdapterRecyclerOrder.MyViewHolder>() {

    var listener: OnAdapterInteractionOrderHistory? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.new_row_fragment, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var order = mList[position]
        holder.bind(order,position)
    }


    fun setdata(List: List<Order>) {
        mList = List
        notifyDataSetChanged()
    }


    interface OnAdapterInteractionOrderHistory {

        fun onItemClicked(itemView: View, order: Order, mList: List<Order>,position: Int)
    }

    fun setAdapterListener(onAdapterInteractionOrderHistory: OnAdapterInteractionOrderHistory) {
        listener = onAdapterInteractionOrderHistory
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(order: Order,position: Int) {

            listener?.onItemClicked(itemView, order, mList,position)

        }


    }


}