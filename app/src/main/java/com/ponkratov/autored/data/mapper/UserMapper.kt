package com.ponkratov.autored.data.mapper

import com.ponkratov.autored.data.model.UserDTO
import com.ponkratov.autored.domain.model.User

fun User.toData(): UserDTO {
    return UserDTO(
        id, fio, email, phone, birthdate, profileDescription, passportNumber, driverLicenseNumber
    )
}

fun UserDTO.toDomain(): User {
    return User(
        id, fio, email, phone, birthdate, profileDescription, passportNumber, driverLicenseNumber
    )
}