package com.ponkratov.autored.data.model

data class ReviewCarDTO(
    val id: Long = 0,
    val mark: Int,
    val comment: String,
    val userFrom: Long,
    val carTo: Long,
)