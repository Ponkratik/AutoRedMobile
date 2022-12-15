package com.ponkratov.autored.domain.model

import java.util.Date

data class Advertisement(
    val id: Long = 0,
    val userId: Long,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val publicationDate: Date = Date(),
    val verified: Boolean = false,
    val carId: Long = 0,
    val pricePerDay: Double,
    val pricePerWeek: Double,
    val pricePerMonth: Double
)