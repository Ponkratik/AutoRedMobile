package com.ponkratov.autored.data.model

data class SupportRequestDTO(
    val id: Long = 0,
    val userId: Long,
    val message: String,
)