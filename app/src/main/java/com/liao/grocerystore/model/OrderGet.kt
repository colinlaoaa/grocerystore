package com.liao.grocerystore.model

import java.io.Serializable

data class OrderGet(
    val count: Int,
    val `data`: List<Order>,
    val error: Boolean
)

data class Order(
    val date: String,
    val orderSummary: OrderSummary,
    val payment: Payment,
    val products: List<Product>,
    val shippingAddress: ShippingAddress,
    val user: UserOrder,
    val userId: String
):Serializable

data class OrderSummary(
    val _id: String,
    val deliveryCharges: Double,
    val discount: Double,
    val orderAmount: Double,
    val ourPrice: Double,
    val totalAmount: Double
):Serializable

data class Payment(
    val _id: String,
    val paymentMode: String
):Serializable



data class ShippingAddress(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String
):Serializable

data class UserOrder(
    val _id: String,
    val email: String,
    val mobile: String,
    val name: String
):Serializable