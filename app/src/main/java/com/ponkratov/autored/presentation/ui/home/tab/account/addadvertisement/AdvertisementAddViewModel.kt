package com.ponkratov.autored.presentation.ui.home.tab.account.addadvertisement

import android.R.attr.data
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.autored.domain.model.Advertisement
import com.ponkratov.autored.domain.model.Car
import com.ponkratov.autored.domain.model.CarFeatureList
import com.ponkratov.autored.domain.usecase.AddAdvertisementUseCase
import com.ponkratov.autored.domain.usecase.GetJwtResponseUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import java.io.File
import java.util.*


class AdvertisementAddViewModel(
    private val addAdvertisementUseCase: AddAdvertisementUseCase,
    private val getJwtResponseUseCase: GetJwtResponseUseCase
) : ViewModel() {

    private var initFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val loadingFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val errorFlow = MutableSharedFlow<Throwable>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow = MutableSharedFlow<String>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val getResponseFlow = initFlow
        .onEach {
            loadingFlow.tryEmit(Unit)
        }
        .onEach {
            addAdvertisementUseCase(
                requireNotNull(advertisement),
                requireNotNull(car),
                requireNotNull(carFeatureList),
                files
            )
                .fold(
                    onSuccess = {
                        dataFlow.tryEmit(it)
                    },
                    onFailure = {
                        errorFlow.tryEmit(it)
                    }
                )
        }.shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    private var advertisement: Advertisement? = null
    private var car: Car? = null
    private var carFeatureList: CarFeatureList? = null
    private var files: List<File> = emptyList()

    fun onSendButtonClicked(
        isConditioner: Boolean,
        isAllWheelDrive: Boolean,
        isLeatherSeats: Boolean,
        isChildSeats: Boolean,
        isHeatedSeats: Boolean,
        isCooledSeats: Boolean,
        isGps: Boolean,
        isSkiRack: Boolean,
        isAudioInput: Boolean,
        isUsbInput: Boolean,
        isBluetooth: Boolean,
        isAndroidAuto: Boolean,
        isAppleCarplay: Boolean,
        isSunRoof: Boolean,
        vin: String,
        licensePlate: String,
        make: String,
        model: String,
        manufacturedYear: Date,
        transmissionType: String,
        fuelType: String,
        doors: Int,
        seats: Int,
        carType: String,
        color: String,
        location: String,
        latitude: Double,
        longitude: Double,
        pricePerDay: Double,
        pricePerWeek: Double,
        pricePerMonth: Double,
        filesList: List<File>
    ) {
        carFeatureList = CarFeatureList(
            0,
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

        car = Car(
            vin = vin,
            licensePlate = licensePlate,
            make = make,
            model = model,
            manufacturedYear = manufacturedYear,
            transmissionType = transmissionType,
            fuelType = fuelType,
            doors = doors,
            seats = seats,
            carType = carType,
            color = color
        )

        advertisement = Advertisement(
            userId = getJwtResponseUseCase().id,
            location = location,
            latitude = latitude,
            longitude = longitude,
            pricePerDay = pricePerDay,
            pricePerWeek = pricePerWeek,
            pricePerMonth = pricePerMonth
        )

        files = filesList

        initFlow.tryEmit(Unit)
    }

}