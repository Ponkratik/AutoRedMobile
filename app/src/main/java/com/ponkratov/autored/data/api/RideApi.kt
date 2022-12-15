package com.ponkratov.autored.data.api

import com.ponkratov.autored.data.model.response.AdvertisementResponseDTO
import com.ponkratov.autored.data.model.response.MessageResponse
import com.ponkratov.autored.data.model.response.RideResponseDTO
import retrofit2.http.*

interface RideApi {

    @Multipart
    @POST("ride/book/start")
    suspend fun bookRide(
        @Part("advertisementId") advertisementId: Long,
        @Part("lessorId") lessorId: Long
    ): MessageResponse

    @GET("ride/get/all/full/advertisement/{id}")
    suspend fun getRideResponsesByAdvertisementId(@Path("id") advertisementId: Long): List<RideResponseDTO>

    @GET("ride/get/all/full/lessor/{id}")
    suspend fun getRideResponsesByLessorId(@Path("id") lessorId: Long): List<RideResponseDTO>
}