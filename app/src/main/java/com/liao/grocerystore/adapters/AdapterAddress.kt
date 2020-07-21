package com.liao.grocerystore.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liao.grocerystore.R
import com.liao.grocerystore.activity.PaymentActivity
import com.liao.grocerystore.model.Address
import kotlinx.android.synthetic.main.new_row.view.text_view_1
import kotlinx.android.synthetic.main.new_row_address.view.*

class AdapterAddress(var mContext: Context, var mList: List<Address>) :
    RecyclerView.Adapter<AdapterAddress.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.new_row_address, parent, false)
        var viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    fun setList(list: List<Address>){
         mList = list
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var category = mList[position]
        holder.bind(category)
    }




    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(address: Address) {
            itemView.text_view_1.text = address.city
            itemView.text_view_2.text = address.houseNo
            itemView.text_view_3.text = address.streetName
            itemView.text_view_4.text = address.type
            itemView.text_view_5.text = address.pincode.toString()

            itemView.recycler_view_address_row.setOnClickListener {
                var myIntent = Intent(mContext, PaymentActivity::class.java)
                myIntent.putExtra(Address.KEY,address)
                mContext.startActivity(myIntent)
            }

        }
    }
}