package com.ponkratov.autored.data.repository

import com.ponkratov.autored.data.api.AdvertisementApi
import com.ponkratov.autored.data.mapper.toData
import com.ponkratov.autored.data.mapper.toDomain
import com.ponkratov.autored.domain.model.Advertisement
import com.ponkratov.autored.domain.model.Car
import com.ponkratov.autored.domain.model.CarFeatureList
import com.ponkratov.autored.domain.model.response.AdvertisementResponse
import com.ponkratov.autored.domain.repository.AdvertisementRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AdvertisementRepositoryImpl(
    private val advertisementApi: AdvertisementApi
) : AdvertisementRepository {
    override suspend fun getAdvertisementsResponse(): Result<List<AdvertisementResponse>> =
        runCatching {
            advertisementApi.getAdvertisementsResponse()
                .map {
                    it.toDomain()
                }
        }

    override suspend fun getAdvertisementsResponseByUserId(userId: Long): Result<List<AdvertisementResponse>> =
        runCatching {
            advertisementApi.getAdvertisementsResponseByUserId(userId)
                .map {
                    it.toDomain()
                }
        }

    override suspend fun getAdvertisementResponse(id: Long): Result<AdvertisementResponse> =
        runCatching {
            advertisementApi.getAdvertisementResponse(id).toDomain()
        }


    override suspend fun addAdvertisement(
        advertisement: Advertisement,
        car: Car,
        carFeatureList: CarFeatureList,
        files: List<File>
    ): Result<String> = runCatching {
        val fileBodys = files.map {
            MultipartBody.Part.createFormData(
                "files",
                it.name,
                it.asRequestBody("image/*".toMediaTypeOrNull())
            )
        }

        advertisementApi.addAdvertisement(
            advertisement.toData(),
            car.toData(),
            carFeatureList.toData(),
            fileBodys
        ).message
    }
}