package com.ponkratov.autored.domain.model

data class SupportRequest(
    val id: Long = 0,
    val userId: Long,
    val message: String,
)