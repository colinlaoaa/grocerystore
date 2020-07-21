package com.liao.grocerystore.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.model.CartContent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.new_row_cart.view.*


class AdapterRecyclerCart(var mContext: Context, var mList: List<CartContent>) :
    RecyclerView.Adapter<AdapterRecyclerCart.MyViewHolder>() {

    var listener: OnAdapterInteraction? = null



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
        holder.bind(cartContent, position)
    }

    interface OnAdapterInteraction {

        fun onItemClickedRemove(cartContent: CartContent)
        fun onAddButtonClicked(itemView: View, cartContent: CartContent)
        fun onMinusButtonClicked(itemView: View, cartContent: CartContent)
    }

    fun setAdapterListener(onAdapterInteraction: OnAdapterInteraction) {
        listener = onAdapterInteraction
    }

    fun checkout():ArrayList<Double> {
        notifyDataSetChanged()
        var subtotal: Double = 0.0
        var saving: Double = 0.0
        var tax: Double = 0.0
        var total = 0.0
        for (product in mList) {
            subtotal += product.quantity * product.price
            saving += product.quantity * (product.mrp - product.price)
            tax += product.quantity * product.price*0.02
        }
        total = subtotal + tax
        var res = ArrayList<Double>()
        res.add(subtotal)
        res.add(saving)
        res.add(tax)
        res.add(total)
        return res
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cartContent: CartContent, position: Int) {

            itemView.text_view_1.text = cartContent.productName
            itemView.text_view_2.text = cartContent.mrp.toString()
            itemView.text_view_2.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.text_view_3.text = cartContent.price.toString()
            itemView.text_view_4.text = cartContent.quantity.toString()



            Picasso.get().load(Config.IMAGE_BASE_URL + cartContent.image)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(itemView.image_view_1)


            itemView.button_remove.setOnClickListener {
                mList -= mList[position]
                listener?.onItemClickedRemove(cartContent)
                notifyItemRemoved(position)
                notifyItemChanged(position, mList.size)

            }


            itemView.button_add.setOnClickListener {
                listener?.onAddButtonClicked(itemView, cartContent)
            }

            itemView.button_minus.setOnClickListener {

                if (itemView.text_view_4.text == "1") {
//                    mList -= mList[position]
//                    listener?.onItemClickedRemove(cartContent)
//                    notifyItemRemoved(position)
//                    notifyItemChanged(position, mList.size)
                } else {
                    listener?.onMinusButtonClicked(itemView, cartContent)

                }
            }


        }
    }
}