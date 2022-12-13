package com.ponkratov.autored.data.repository

import com.ponkratov.autored.data.api.RideApi
import com.ponkratov.autored.domain.repository.RideRepository

class RideRepositoryImpl(
    private val rideApi: RideApi
) : RideRepository {
    override suspend fun bookRide(advertisementId: Long, lessorId: Long): Result<String> =
        runCatching {
            rideApi.bookRide(advertisementId, lessorId).message
        }
}