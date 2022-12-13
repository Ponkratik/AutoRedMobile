package com.ponkratov.autored.data.mapper

import com.ponkratov.autored.data.model.CarDTO
import com.ponkratov.autored.domain.model.Car

fun Car.toData(): CarDTO {
    return CarDTO(
        id,
        vin,
        licensePlate,
        make,
        model,
        manufacturedYear,
        transmissionType,
        fuelType,
        doors,
        seats,
        carType,
        color
    )
}

fun CarDTO.toDomain(): Car {
    return Car(
        id,
        vin,
        licensePlate,
        make,
        model,
        manufacturedYear,
        transmissionType,
        fuelType,
        doors,
        seats,
        carType,
        color
    )
}