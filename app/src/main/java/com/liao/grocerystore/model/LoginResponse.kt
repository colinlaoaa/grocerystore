package com.liao.myapplication.model

data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    val __v: Int,
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