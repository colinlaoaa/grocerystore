package com.liao.grocerystore.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.model.ProductData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.new_row.view.text_view_1
import kotlinx.android.synthetic.main.new_row_fragment.view.*

class AdapterRecyclerFragment(var mContext: Context, var mList: ArrayList<ProductData>) :
    RecyclerView.Adapter<AdapterRecyclerFragment.MyViewHolder>() {

    var listener: OnAdapterInteraction? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.new_row_fragment, parent, false)
        var viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var productData = mList[position]
        holder.bind(productData)
    }


    fun setdata(List: ArrayList<ProductData>) {
        mList = List
        notifyDataSetChanged()
    }


    interface OnAdapterInteraction {

        fun onItemClicked(itemView: View, productData: ProductData)
    }

    fun setAdapterListener (onAdapterInteraction: OnAdapterInteraction) {
        listener = onAdapterInteraction
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dbHelper = DBHelper(mContext)

        fun bind(productData: ProductData) {
            itemView.progress_bar.visibility = View.GONE
            itemView.text_view_1.text = productData.productName
            itemView.text_view_2.text = "unit" + productData.unit
            itemView.text_view_3.text = productData.mrp.toString()
            itemView.text_view_4.text = productData.price.toString()
            itemView.text_view_3.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            Picasso.get().load(Config.IMAGE_BASE_URL + productData.image)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(itemView.image_view_1)

            itemView.button_2.visibility = View.GONE
            itemView.button_3.visibility = View.GONE

            listener?.onItemClicked(itemView, productData)


        }


    }


}