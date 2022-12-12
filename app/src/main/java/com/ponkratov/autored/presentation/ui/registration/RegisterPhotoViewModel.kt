package com.ponkratov.autored.presentation.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.autored.domain.model.request.RegisterRequest
import com.ponkratov.autored.domain.usecase.RegisterUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import java.io.File
import java.util.*

class RegisterPhotoViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private var authFlow = MutableSharedFlow<Unit>(
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

    private var registerRequest: RegisterRequest? = null
    private var avatarPhoto: File? = null
    private var passportPhoto: File? = null
    private var driverLicensePhoto: File? = null

    val registerFlow = authFlow
        .onEach {
            loadingFlow.tryEmit(Unit)
        }
        .onEach {
            registerUseCase(
                requireNotNull(registerRequest),
                requireNotNull(avatarPhoto),
                requireNotNull(passportPhoto),
                requireNotNull(driverLicensePhoto)
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

    fun onRegisterButtonClicked(
        fio: String,
        email: String,
        rawPassword: String,
        phone: String,
        birthdate: Date,
        passportNumber: String,
        driverLicenseNumber: String,
        profileDescription: String,
        profilePhoto: File,
        passportPhoto: File,
        driverLicensePhoto: File
    ) {
        registerRequest = RegisterRequest(
            fio,
            email,
            rawPassword,
            phone,
            birthdate,
            passportNumber,
            driverLicenseNumber,
            profileDescription
        )
        avatarPhoto = profilePhoto
        this.passportPhoto = passportPhoto
        this.driverLicensePhoto = driverLicensePhoto

        authFlow.tryEmit(Unit)
    }
}