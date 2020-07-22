package com.liao.grocerystore.model

data class RegisterResponse(
    val data: UserInfo,
    val error: Boolean,
    val message: String
)

data class UserInfo(
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