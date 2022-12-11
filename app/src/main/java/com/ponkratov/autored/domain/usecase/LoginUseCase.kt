package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.model.request.LoginRequest
import com.ponkratov.autored.domain.model.response.JwtResponse
import com.ponkratov.autored.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest): Result<JwtResponse> =
        authRepository.login(loginRequest)
}