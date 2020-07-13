package com.liao.grocerystore.adapters

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liao.grocerystore.R
import com.liao.grocerystore.activity.SubCategoryActivity
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.model.Category
import com.liao.grocerystore.model.CategoryResponse
import com.liao.grocerystore.model.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.new_row.view.*


class AdapterRecycler(var mContext: Context, var mList: List<Category>) :
    RecyclerView.Adapter<AdapterRecycler.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.new_row, parent, false)
        var viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var category = mList[position]
        holder.bind(category)
    }

    fun setdata(list: ArrayList<Category>) {
        mList = list
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) {
            itemView.text_view_1.text = category.catName
            Picasso.get().load(Config.IMAGE_BASE_URL + category.catImage).placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(itemView.image_view_2)

            itemView.image_view_2.setOnClickListener{
                var count = mList.size
                var myIntent = Intent(mContext, SubCategoryActivity::class.java)
                myIntent.putExtra("COUNT",count)
                var newList = arrayListOf<String>()
                for(i in 0 .. count-1){

                        newList.add(mList[i].catName)
                }
                myIntent.putExtra("CATNAME",newList)

                mContext.startActivity(myIntent)
            }




        }
    }
}