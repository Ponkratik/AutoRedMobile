package com.ponkratov.autored.data.model.request

import java.util.*

data class RegisterRequestDTO(
    val fio: String,
    val email: String,
    val rawPassword: String,
    val phone: String,
    val birthdate: Date,
    val passportNumber: String,
    val driverLicenseNumber: String,
    val profileDescription: String
)