package com.ponkratov.autored.data.mapper

import com.ponkratov.autored.data.model.RideDTO
import com.ponkratov.autored.domain.model.Ride

fun RideDTO.toDomain(): Ride {
    return Ride(
        id,
        advertisementId,
        lessorId,
        dateStart,
        dateEnd,
        dateSignedLessor,
        dateSignedLessee,
        chatLink,
        paymentLink,
        totalCost
    )
}

fun Ride.toData(): RideDTO {
    return RideDTO(
        id,
        advertisementId,
        lessorId,
        dateStart,
        dateEnd,
        dateSignedLessor,
        dateSignedLessee,
        chatLink,
        paymentLink,
        totalCost
    )
}