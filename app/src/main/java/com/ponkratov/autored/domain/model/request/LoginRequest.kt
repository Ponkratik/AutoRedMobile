package com.ponkratov.autored.domain.model.request

data class LoginRequest(
    val email: String,
    val password: String
)