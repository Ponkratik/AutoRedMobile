package com.ponkratov.autored.data.model

import java.util.*

data class AdvertisementDTO(
    val id: Long,
    val userId: Long,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val publicationDate: Date,
    val verified: Boolean,
    val carId: Long,
    val pricePerDay: Double,
    val pricePerWeek: Double,
    val pricePerMonth: Double
)