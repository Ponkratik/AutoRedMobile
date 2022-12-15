package com.ponkratov.autored.data.model.response

import com.ponkratov.autored.data.model.RideDTO
import com.ponkratov.autored.data.model.UserDTO

data class RideResponseDTO(
    val ride: RideDTO,
    val user: UserDTO,
    val advertisementResponse: AdvertisementResponseDTO
)