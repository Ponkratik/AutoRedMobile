package com.ponkratov.autored.domain.model.response

data class JwtResponse(
    val accessToken: String,
    val id: Long,
    val fio: String,
    val email: String,
    val phone: String,
    val roles: List<String>
)