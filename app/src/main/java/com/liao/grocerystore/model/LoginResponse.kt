package com.liao.grocerystore.model

data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
)
data class LoginFailResponse(
    val error: Boolean,
    val message: String
)