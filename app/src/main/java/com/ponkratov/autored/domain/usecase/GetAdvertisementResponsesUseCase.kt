package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.repository.AdvertisementRepository

class GetAdvertisementResponsesUseCase(
    private val advertisementRepository: AdvertisementRepository
) {

    suspend operator fun invoke() = advertisementRepository.getAdvertisementsResponse()
}