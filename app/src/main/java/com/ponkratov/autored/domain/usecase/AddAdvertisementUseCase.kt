package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.model.Advertisement
import com.ponkratov.autored.domain.model.Car
import com.ponkratov.autored.domain.model.CarFeatureList
import com.ponkratov.autored.domain.repository.AdvertisementRepository
import java.io.File

class AddAdvertisementUseCase(
    private val advertisementRepository: AdvertisementRepository
) {

    suspend operator fun invoke(
        advertisement: Advertisement,
        car: Car,
        carFeatureList: CarFeatureList,
        files: List<File>
    ) = advertisementRepository.addAdvertisement(advertisement, car, carFeatureList, files)
}