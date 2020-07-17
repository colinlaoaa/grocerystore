package com.liao.grocerystore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.model.CartContent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.new_row_cart.view.*


class AdapterRecycerCart(var mContext: Context, var mList: List<CartContent>) :
    RecyclerView.Adapter<AdapterRecycerCart.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.new_row_cart, parent, false)
        var viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var cartContent = mList[position]
        holder.bind(cartContent)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cartContent: CartContent) {
            itemView.text_view_1.text = cartContent._id
            itemView.text_view_2.text = cartContent.productName
            itemView.text_view_3.text = cartContent.mrp.toString()
            itemView.text_view_4.text = cartContent.price.toString()
            itemView.text_view_5.text = cartContent.quantity.toString()


            Picasso.get().load(Config.IMAGE_BASE_URL + cartContent.image).placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(itemView.image_view_1)

        }
    }
}