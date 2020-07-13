package com.liao.grocerystore.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.model.Data

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.new_row.view.*

class AdapterRecyclerFragment(var mContext: Context, var mList: ArrayList<Data>) :
    RecyclerView.Adapter<AdapterRecyclerFragment.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.new_row, parent, false)
        var viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data2 = mList[position]
        holder.bind(data2)
    }


    fun setdata(List: ArrayList<Data>) {
        mList = List
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data2: Data) {
            itemView.text_view_1.text = data2.subName
            Picasso.get().load(Config.IMAGE_BASE_URL + data2.subImage).placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(itemView.image_view_2)



        }
    }




}