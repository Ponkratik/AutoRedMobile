package com.ponkratov.autored.domain.model.response

import com.ponkratov.autored.data.model.RideDTO
import com.ponkratov.autored.data.model.UserDTO
import com.ponkratov.autored.domain.model.Ride
import com.ponkratov.autored.domain.model.User

data class RideResponse(
    val ride: Ride,
    val user: User,
    val advertisementResponse: AdvertisementResponse
)