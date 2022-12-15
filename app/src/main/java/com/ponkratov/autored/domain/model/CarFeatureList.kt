package com.ponkratov.autored.domain.model

data class CarFeatureList(
    val carId: Long = 0,
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

fun CarFeatureList.toListOptions(): List<String> {
    val list = mutableListOf<String>()
    if (isConditioner) list.add("Кондиционер")
    if (isAllWheelDrive) list.add("Полный привод")
    if (isLeatherSeats) list.add("Кожаный салон")
    if (isChildSeats) list.add("Детское кресло")
    if (isHeatedSeats) list.add("Подогрев сидений")
    if (isCooledSeats) list.add("Вентиляция сидений")
    if (isGps) list.add("Навигация")
    if (isSkiRack) list.add("Лючок для лыж")
    if (isAudioInput) list.add("Аудио-вход")
    if (isUsbInput) list.add("USB-вход")
    if (isBluetooth) list.add("Bluetooth")
    if (isAndroidAuto) list.add("Android Auto")
    if (isAppleCarplay) list.add("Apple Carplay")
    if (isSunRoof) list.add("Люк/Панорамная крыша")

    return list
}