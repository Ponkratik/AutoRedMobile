package com.ponkratov.autored.data.api

import com.ponkratov.autored.data.model.response.MessageResponse
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RideApi {

    @Multipart
    @POST("ride/book/start")
    suspend fun bookRide(
        @Part("advertisementId") advertisementId: Long,
        @Part("lessorId") lessorId: Long
    ): MessageResponse
}