package com.ponkratov.autored.data.repository

import com.ponkratov.autored.data.api.RideApi
import com.ponkratov.autored.data.mapper.toDomain
import com.ponkratov.autored.domain.model.response.RideResponse
import com.ponkratov.autored.domain.repository.RideRepository

class RideRepositoryImpl(
    private val rideApi: RideApi
) : RideRepository {
    override suspend fun bookRide(advertisementId: Long, lessorId: Long): Result<String> =
        runCatching {
            rideApi.bookRide(advertisementId, lessorId).message
        }

    override suspend fun startRide(rideId: Long): Result<String> = runCatching {
        rideApi.startRide(rideId).message
    }

    override suspend fun endRide(rideId: Long): Result<String> = runCatching {
        rideApi.endRide(rideId).message
    }

    override suspend fun signActByLessor(rideId: Long): Result<String> = runCatching {
        rideApi.signActByLessor(rideId).message
    }

    override suspend fun signActByLessee(rideId: Long): Result<String> = runCatching {
        rideApi.signActByLessee(rideId).message
    }

    override suspend fun getRideResponsesByAdvertisementId(advertisementId: Long): Result<List<RideResponse>> =
        runCatching {
            rideApi.getRideResponsesByAdvertisementId(advertisementId).map {
                it.toDomain()
            }
        }

    override suspend fun getRideResponsesByLessorId(lessorId: Long): Result<List<RideResponse>> =
        runCatching {
            rideApi.getRideResponsesByLessorId(lessorId).map {
                it.toDomain()
            }
        }
}