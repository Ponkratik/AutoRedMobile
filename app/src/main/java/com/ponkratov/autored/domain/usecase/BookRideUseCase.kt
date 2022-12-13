package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.repository.RideRepository

class BookRideUseCase(
    private val rideRepository: RideRepository
) {

    suspend operator fun invoke(advertisementId: Long, lessorId: Long) = rideRepository.bookRide(advertisementId, lessorId)
}