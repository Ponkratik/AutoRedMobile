package com.ponkratov.autored.domain.repository

import com.ponkratov.autored.domain.model.request.LoginRequest
import com.ponkratov.autored.domain.model.request.RegisterRequest
import com.ponkratov.autored.domain.model.response.JwtResponse
import java.io.File
import java.util.*

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Result<JwtResponse>

    suspend fun register(
        registerRequest: RegisterRequest,
        profilePhoto: File,
        passportPhoto: File,
        driverLicensePhoto: File
    ) : Result<String>
}