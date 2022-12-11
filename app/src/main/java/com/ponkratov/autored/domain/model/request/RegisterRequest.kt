package com.ponkratov.autored.domain.model.request

import java.util.*

data class RegisterRequest(
    val fio: String,
    val email: String,
    val rawPassword: String,
    val phone: String,
    val birthdate: Date,
    val passportNumber: String,
    val driverLicenseNumber: String,
    val profileDescription: String
)