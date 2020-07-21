package com.liao.grocerystore.model

data class OrderPost(
    val `data`: OrderInfo,
    val error: Boolean,
    val message: String
)

data class OrderInfo(
    val __v: Int,
    val _id: String,
    val date: String,
    val products: List<Product>,
    val userId: String
)

data class Product(
    val _id: String,
    val image: String,
    val mrp: Int,
    val price: Int,
    val productName: String,
    val quantity: Int
)