package com.ponkratov.autored.data.api

import com.ponkratov.autored.data.model.SupportRequestDTO
import com.ponkratov.autored.data.model.response.MessageResponse
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SupportRequestApi {

    @Multipart
    @POST("supportrequest/add")
    suspend fun createSupportRequest(
        @Part("supportrequest") supportRequest: SupportRequestDTO
    ): MessageResponse
}