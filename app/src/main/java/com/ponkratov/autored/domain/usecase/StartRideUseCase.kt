package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.repository.RideRepository

class StartRideUseCase(
    private val rideRepository: RideRepository
) {

    suspend operator fun invoke(rideId: Long) = rideRepository.startRide(rideId)
}