package com.ponkratov.autored.data.api

import com.ponkratov.autored.data.model.ReviewCarDTO
import com.ponkratov.autored.data.model.ReviewUserDTO
import com.ponkratov.autored.data.model.response.MessageResponse
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ReviewApi {

    @Multipart
    @POST("review/car/add")
    suspend fun addCarReview(
        @Part("review") reviewCar: ReviewCarDTO
    ): MessageResponse

    @Multipart
    @POST("review/user/add")
    suspend fun addUserReview(
        @Part("review") reviewUser: ReviewUserDTO
    ): MessageResponse
}