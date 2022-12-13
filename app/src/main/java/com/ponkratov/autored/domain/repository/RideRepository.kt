package com.ponkratov.autored.domain.repository

interface RideRepository {

    suspend fun bookRide(advertisementId: Long, lessorId: Long): Result<String>


}