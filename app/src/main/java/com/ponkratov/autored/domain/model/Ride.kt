package com.ponkratov.autored.domain.model

import java.util.*

data class Ride(
    var id: Long = 0,
    var advertisementId: Long,
    var lessorId: Long,
    var dateStart: Date,
    var dateEnd: Date,
    var dateSignedLessor: Date,
    var dateSignedLessee: Date,
    var chatLink: String,
    var paymentLink: String,
    var totalCost: Double,
)