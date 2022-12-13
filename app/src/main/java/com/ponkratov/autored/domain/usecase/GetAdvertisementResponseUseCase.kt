package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.repository.AdvertisementRepository

class GetAdvertisementResponseUseCase(
    private val advertisementRepository: AdvertisementRepository
) {

    suspend operator fun invoke(id: Long) = advertisementRepository.getAdvertisementResponse(id)
}