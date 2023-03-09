package com.ponkratov.autored.domain.repository

import com.ponkratov.autored.domain.model.response.RideResponse
import retrofit2.http.Path

interface RideRepository {

    suspend fun bookRide(advertisementId: Long, lessorId: Long): Result<String>

    suspend fun startRide(rideId: Long): Result<String>

    suspend fun endRide(rideId: Long): Result<String>

    suspend fun signActByLessor(rideId: Long): Result<String>

    suspend fun signActByLessee(rideId: Long): Result<String>

    suspend fun getRideResponsesByAdvertisementId(@Path("id") advertisementId: Long): Result<List<RideResponse>>

    suspend fun getRideResponsesByLessorId(@Path("id") lessorId: Long): Result<List<RideResponse>>

}