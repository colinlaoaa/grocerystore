package com.liao.grocerystore.model

data class OrderPost(
    val `data`: OrderInfo,
    val error: Boolean,
    val message: String
)

data class OrderInfo(
    val date: String,
    val products: List<Product>,
    val userId: String
)

data class Product(
    val image: String,
    val mrp: Double,
    val price: Double,
    val productName: String,
    val quantity: Int
)

