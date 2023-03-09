package com.ponkratov.autored.data.api

import com.ponkratov.autored.data.model.response.MessageResponse
import com.ponkratov.autored.data.model.response.RideResponseDTO
import retrofit2.http.*

interface RideApi {

    @Multipart
    @POST("ride/book/book")
    suspend fun bookRide(
        @Part("advertisementId") advertisementId: Long,
        @Part("lessorId") lessorId: Long
    ): MessageResponse

    @POST("ride/book/start/{id}")
    suspend fun startRide(
        @Path("rideId") rideId: Long
    ): MessageResponse

    @POST("ride/book/end/{id}")
    suspend fun endRide(
        @Path("rideId") rideId: Long
    ): MessageResponse

    @POST("sign/lessor/{id}")
    suspend fun signActByLessor(
        @Path("rideId") rideId: Long
    ): MessageResponse

    @POST("sign/lessee/{id}")
    suspend fun signActByLessee(
        @Path("rideId") rideId: Long
    ): MessageResponse

    @GET("ride/get/all/full/advertisement/{id}")
    suspend fun getRideResponsesByAdvertisementId(@Path("id") advertisementId: Long): List<RideResponseDTO>

    @GET("ride/get/all/full/lessor/{id}")
    suspend fun getRideResponsesByLessorId(@Path("id") lessorId: Long): List<RideResponseDTO>
}