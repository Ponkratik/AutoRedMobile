package com.ponkratov.autored.domain.model

import java.util.*

data class User(
    val id: Long,
    val fio: String,
    val email: String,
    val phone: String,
    val birthdate: Date,
    val profileDescription: String,
    val passportNumber: String,
    val driverLicenseNumber: String,
)