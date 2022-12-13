package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.model.response.JwtResponse
import com.ponkratov.autored.domain.repository.SharedPrefsRepository

class SaveJwtResponseUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository
) {

    operator fun invoke(jwtResponse: JwtResponse) = sharedPrefsRepository.putJwt(jwtResponse)
}