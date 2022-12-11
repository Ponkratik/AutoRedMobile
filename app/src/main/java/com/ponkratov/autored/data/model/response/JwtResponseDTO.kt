package com.ponkratov.autored.data.model.response

data class JwtResponseDTO(
    val accessToken: String,
    val id: Long,
    val fio: String,
    val email: String,
    val phone: String,
    val roles: List<String>
)