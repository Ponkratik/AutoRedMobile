package com.ponkratov.autored.data.mapper

import com.ponkratov.autored.data.model.CarFeatureListDTO
import com.ponkratov.autored.domain.model.CarFeatureList

fun CarFeatureList.toData(): CarFeatureListDTO {
    return CarFeatureListDTO(
        carId,
        isConditioner,
        isAllWheelDrive,
        isLeatherSeats,
        isChildSeats,
        isHeatedSeats,
        isCooledSeats,
        isGps,
        isSkiRack,
        isAudioInput,
        isUsbInput,
        isBluetooth,
        isAndroidAuto,
        isAppleCarplay,
        isSunRoof
    )
}

fun CarFeatureListDTO.toDomain(): CarFeatureList {
    return CarFeatureList(
        carId,
        isConditioner,
        isAllWheelDrive,
        isLeatherSeats,
        isChildSeats,
        isHeatedSeats,
        isCooledSeats,
        isGps,
        isSkiRack,
        isAudioInput,
        isUsbInput,
        isBluetooth,
        isAndroidAuto,
        isAppleCarplay,
        isSunRoof
    )
}