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
    var _id: String? = null,
    var city: String,
    var houseNo: String,
    var pincode: Int,
    var streetName: String,
    var type: String,
    var userId: String
):Serializable{
    companion object{
        const val KEY = "address"
    }
}