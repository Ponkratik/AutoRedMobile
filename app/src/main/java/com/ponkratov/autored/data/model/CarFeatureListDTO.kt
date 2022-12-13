package com.ponkratov.autored.data.model

data class CarFeatureListDTO(
    val carId: Long,
    val isConditioner: Boolean,
    val isAllWheelDrive: Boolean,
    val isLeatherSeats: Boolean,
    val isChildSeats: Boolean,
    val isHeatedSeats: Boolean,
    val isCooledSeats: Boolean,
    val isGps: Boolean,
    val isSkiRack: Boolean,
    val isAudioInput: Boolean,
    val isUsbInput: Boolean,
    val isBluetooth: Boolean,
    val isAndroidAuto: Boolean,
    val isAppleCarplay: Boolean,
    val isSunRoof: Boolean
)