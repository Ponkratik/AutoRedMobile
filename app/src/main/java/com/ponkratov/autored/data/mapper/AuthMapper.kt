package com.ponkratov.autored.data.mapper

import com.ponkratov.autored.data.model.request.LoginRequestDTO
import com.ponkratov.autored.data.model.request.RegisterRequestDTO
import com.ponkratov.autored.data.model.response.JwtResponseDTO
import com.ponkratov.autored.domain.model.request.LoginRequest
import com.ponkratov.autored.domain.model.request.RegisterRequest
import com.ponkratov.autored.domain.model.response.JwtResponse

fun JwtResponse.toData(): JwtResponseDTO {
    return JwtResponseDTO(accessToken, id, fio, email, phone, roles)
}

fun JwtResponseDTO.toDomain(): JwtResponse {
    return JwtResponse(accessToken, id, fio, email, phone, roles)
}

fun LoginRequest.toData(): LoginRequestDTO {
    return LoginRequestDTO(email, password)
}

fun RegisterRequest.toData(): RegisterRequestDTO {
    return RegisterRequestDTO(
        fio,
        email,
        rawPassword,
        phone,
        birthdate,
        passportNumber,
        driverLicenseNumber,
        profileDescription
    )
}