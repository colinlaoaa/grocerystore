package com.liao.grocerystore.model

data class CartContent(
    var _id: String,
    var productName: String,
    var price: Double,
    var mrp: Double,
    var image: String,
    var quantity: Int
) {
}