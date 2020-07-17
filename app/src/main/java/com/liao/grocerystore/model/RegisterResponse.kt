package com.liao.myapplication.model

data class RegisterResponse(
    val data: Data,
    val error: Boolean,
    val message: String
)

data class Data(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
){
    companion object{
        const val EMAIL_KEY = "email"
    }
}