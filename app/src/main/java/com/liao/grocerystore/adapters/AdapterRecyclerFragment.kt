package com.liao.grocerystore.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.liao.grocerystore.R
import com.liao.grocerystore.activity.ProductDetailActivity
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.helper.DBHelper
import com.liao.grocerystore.model.CartContent
import com.liao.grocerystore.model.ProductData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.new_row.view.text_view_1
import kotlinx.android.synthetic.main.new_row_fragment.view.*

class AdapterRecyclerFragment(var mContext: Context, var mList: ArrayList<ProductData>) :
    RecyclerView.Adapter<AdapterRecyclerFragment.MyViewHolder>() {

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

            itemView.view_2.setOnClickListener {
                var myIntent = Intent(mContext, ProductDetailActivity::class.java)
                myIntent.putExtra(ProductData.PRODUCT_DETAIL, productData)
                mContext.startActivity(myIntent)
            }



            itemView.button_2.visibility = View.GONE
            itemView.button_3.visibility = View.GONE

            var cartContent = CartContent(
                productData._id,
                productData.productName,
                productData.price,
                productData.mrp,
                productData.image,
                0
            )

            var quantity =dbHelper.readCartContentItemQuantity(cartContent._id)
            cartContent.quantity =quantity
            itemView.text_view_5.text = quantity.toString()

            if(itemView.text_view_5.text != "0"){
                itemView.button_1.visibility = View.GONE
                itemView.button_2.visibility = View.VISIBLE
                itemView.button_3.visibility = View.VISIBLE
            }else{
                itemView.button_1.visibility = View.VISIBLE
                itemView.button_2.visibility = View.GONE
                itemView.button_3.visibility = View.GONE
            }


            itemView.button_1.setOnClickListener {
                itemView.button_1.visibility = View.GONE

                itemView.button_2.visibility = View.VISIBLE
                itemView.button_3.visibility = View.VISIBLE
                itemView.text_view_5.visibility = View.VISIBLE


                Toast.makeText(mContext, "added to cart", Toast.LENGTH_SHORT).show()

                if (dbHelper.addquantity(cartContent._id)) {
                    cartContent.quantity += 1
                    dbHelper.updateCartContent(cartContent)
                    itemView.text_view_5.text ="${cartContent.quantity}"
                } else {
                    cartContent.quantity += 1
                    dbHelper.addCartContent(cartContent)
                    itemView.text_view_5.text ="${cartContent.quantity}"

                }


            }

            itemView.button_2.setOnClickListener {


                if (itemView.text_view_5.text == "1") {
                    cartContent.quantity -= 1
                    dbHelper.deleteCartContent(cartContent._id)
                    itemView.button_2.visibility = View.GONE
                    itemView.button_3.visibility = View.GONE
                    itemView.text_view_5.visibility = View.GONE
                    itemView.button_1.visibility = View.VISIBLE
                } else {
                    cartContent.quantity -= 1
                    itemView.text_view_5.text =
                        "${cartContent.quantity}"
                    dbHelper.updateCartContent(cartContent)

                }
            }

            itemView.button_3.setOnClickListener {

                if (dbHelper.addquantity(cartContent._id)) {
                    cartContent.quantity += 1
                    dbHelper.updateCartContent(cartContent)
                }
                else{
                    cartContent.quantity = itemView.text_view_5.text.toString().toInt()
                    cartContent.quantity += 1
                    dbHelper.addCartContent(cartContent)
                }

                if (itemView.text_view_5.text.toString()
                        .toInt() <= productData.quantity.toInt()
                ) {
                    itemView.text_view_5.text =
                        "${cartContent.quantity}"
                    Toast.makeText(
                        mContext,
                        "${productData.quantity.toInt() - itemView.text_view_5.text.toString()
                            .toInt()} left",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        mContext,
                        "only have ${productData.quantity} in stock",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


        }


    }


}