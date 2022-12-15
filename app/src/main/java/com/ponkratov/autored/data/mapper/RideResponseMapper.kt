package com.ponkratov.autored.data.mapper

import com.ponkratov.autored.data.model.response.RideResponseDTO
import com.ponkratov.autored.domain.model.response.RideResponse

fun RideResponseDTO.toDomain(): RideResponse {
    return RideResponse(
        ride.toDomain(),
        user.toDomain(),
        advertisementResponse.toDomain()
    )
}

fun RideResponse.toData(): RideResponseDTO {
    return RideResponseDTO(
        ride.toData(),
        user.toData(),
        advertisementResponse.toData()
    )
}