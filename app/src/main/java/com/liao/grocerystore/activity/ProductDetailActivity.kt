package com.liao.grocerystore.activity

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.liao.grocerystore.R
import com.liao.grocerystore.app.Config
import com.liao.grocerystore.model.ProductContent
import com.liao.grocerystore.model.ProductData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.new_row.view.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        var i =intent
        var productData = i.getSerializableExtra(ProductData.PRODUCT_DETAIL) as ProductData

        Picasso.get().load(Config.IMAGE_BASE_URL + productData.image).placeholder(R.drawable.noimage)
            .error(R.drawable.noimage)
            .into(image_view_3)

        text_view_1.text = "mrp: "+productData.mrp.toString()
        text_view_1.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        text_view_2.text = "price: "+productData.price.toString()
        text_view_3.text = productData.productName
        text_view_4.text = productData.description
        text_view_5.text = "quantity: "+productData.quantity.toString()
    }
}