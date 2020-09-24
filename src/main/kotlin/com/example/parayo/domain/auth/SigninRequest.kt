package com.example.parayo.domain.auth

data class SigninRequest(
    val email: String,
    val password: String,
    val fcmToken: String?
)