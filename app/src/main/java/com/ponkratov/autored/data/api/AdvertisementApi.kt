package com.ponkratov.autored.data.api

import com.ponkratov.autored.data.model.AdvertisementDTO
import com.ponkratov.autored.data.model.CarDTO
import com.ponkratov.autored.data.model.CarFeatureListDTO
import com.ponkratov.autored.data.model.response.AdvertisementResponseDTO
import com.ponkratov.autored.data.model.response.MessageResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AdvertisementApi {

    @GET("advertisement/get/full/all")
    suspend fun getAdvertisementsResponse(): List<AdvertisementResponseDTO>

    @GET("advertisement/get/full/all/{id}")
    suspend fun getAdvertisementsResponseByUserId(@Path("id") userId: Long): List<AdvertisementResponseDTO>

    @GET("advertisement/get/full/{id}")
    suspend fun getAdvertisementResponse(@Path("id") advertisementId: Long): AdvertisementResponseDTO

    @Multipart
    @POST("advertisement/add")
    suspend fun addAdvertisement(
        @Part("advertisement") advertisement: AdvertisementDTO,
        @Part("car") car: CarDTO,
        @Part("car-features") carFeatures: CarFeatureListDTO,
        @Part files: List<MultipartBody.Part>
    ): MessageResponse


}