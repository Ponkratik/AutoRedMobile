package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.repository.AdvertisementRepository

class GetAdvertisementResponsesByUserIdUseCase(
    private val advertisementRepository: AdvertisementRepository
) {

    suspend operator fun invoke(userId: Long) = advertisementRepository.getAdvertisementsResponseByUserId(userId)
}