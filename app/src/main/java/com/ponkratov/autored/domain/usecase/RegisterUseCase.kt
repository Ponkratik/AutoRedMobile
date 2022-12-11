package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.model.request.RegisterRequest
import com.ponkratov.autored.domain.repository.AuthRepository
import java.io.File

class RegisterUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        registerRequest: RegisterRequest,
        profilePhoto: File,
        passportPhoto: File,
        driverLicensePhoto: File
    ): Result<String> = authRepository.register(
        registerRequest,
        profilePhoto,
        passportPhoto,
        driverLicensePhoto
    )
}