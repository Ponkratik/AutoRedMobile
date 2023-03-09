package com.ponkratov.autored.domain.model

data class ReviewUser(
    val id: Long = 0,
    val mark: Int,
    val comment: String,
    val userFrom: Long,
    val userTo: Long,
)