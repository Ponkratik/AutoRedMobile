package com.ponkratov.autored.domain.model.response

import com.ponkratov.autored.domain.model.Advertisement
import com.ponkratov.autored.domain.model.Car
import com.ponkratov.autored.domain.model.CarFeatureList

data class AdvertisementResponse(
    val advertisement: Advertisement,
    val car: Car,
    val carFeatureList: CarFeatureList,
    val photoPaths: List<String>
)