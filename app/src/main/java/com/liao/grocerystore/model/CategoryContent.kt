package com.liao.grocerystore.model

data class CategoryContent(
    val count: Int,
    val data: ArrayList<Data>,
    val error: Boolean
)

data class Data(
    val catId: Int,
    val position: Int,
    val status: Boolean,
    val subDescription: String,
    val subId: Int,
    val subImage: String,
    val subName: String
)