package com.example.parayo.domain.auth

data class SigninResponse(
    val token: String,
    val refreshToken: String,
    val userName: String,
    val userId: Long
)