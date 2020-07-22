package com.liao.grocerystore.model

import java.io.Serializable

data class AddressPost(
    val `data`: Address,
    val error: Boolean,
    val message: String
)





data class AddressGet(
    val count: Int,
    val `data`: List<Address>,
    val error: Boolean
)

data class Address(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
):Serializable{
    companion object{
        const val KEY = "address"
    }
}