package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.repository.RideRepository

class GetRideResponsesByAdvertisementIdUseCase(
    private val rideRepository: RideRepository
) {

    suspend operator fun invoke(advertisementId: Long) = rideRepository.getRideResponsesByAdvertisementId(advertisementId)
}