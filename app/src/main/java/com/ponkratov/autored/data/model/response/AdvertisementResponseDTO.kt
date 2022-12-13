package com.ponkratov.autored.data.model.response

import com.ponkratov.autored.data.model.AdvertisementDTO
import com.ponkratov.autored.data.model.CarDTO
import com.ponkratov.autored.data.model.CarFeatureListDTO

data class AdvertisementResponseDTO(
    val advertisement: AdvertisementDTO,
    val car: CarDTO,
    val carFeatureList: CarFeatureListDTO,
    val photoPaths: List<String>
)