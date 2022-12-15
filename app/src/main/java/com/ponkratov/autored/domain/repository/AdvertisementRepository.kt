package com.ponkratov.autored.domain.repository

import com.ponkratov.autored.domain.model.Advertisement
import com.ponkratov.autored.domain.model.Car
import com.ponkratov.autored.domain.model.CarFeatureList
import com.ponkratov.autored.domain.model.response.AdvertisementResponse
import java.io.File

interface AdvertisementRepository {

    suspend fun getAdvertisementsResponse(): Result<List<AdvertisementResponse>>

    suspend fun getAdvertisementsResponseByUserId(userId: Long): Result<List<AdvertisementResponse>>

    suspend fun getAdvertisementResponse(id: Long): Result<AdvertisementResponse>

    suspend fun addAdvertisement(
        advertisement: Advertisement,
        car: Car,
        carFeatureList: CarFeatureList,
        files: List<File>
    ): Result<String>
}