package com.liao.grocerystore.model

import java.io.Serializable

data class ProductContent(
    val count: Int,
    val data: ArrayList<ProductData>,
    val error: Boolean
)

data class ProductData(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val created: String,
    val description: String,
    val image: String,
    val mrp: Double,
    val position: Int,
    val price: Double,
    val productName: String,
    val quantity: Int,
    val status: Boolean,
    val subId: Int,
    val unit: String
):Serializable{
    companion object{
        const val PRODUCT_DETAIL="KEY"
    }
}