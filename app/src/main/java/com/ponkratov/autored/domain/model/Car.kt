package com.ponkratov.autored.domain.model

import java.util.*

data class Car(
    var id: Long = 0,
    var vin: String,
    var licensePlate: String,
    var make: String,
    var model: String,
    var manufacturedYear: Date,
    var transmissionType: String,
    var fuelType: String,
    var doors: Int,
    var seats: Int,
    var carType: String,
    var color: String
)