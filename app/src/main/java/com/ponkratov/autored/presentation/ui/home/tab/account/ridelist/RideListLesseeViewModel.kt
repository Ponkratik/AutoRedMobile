package com.ponkratov.autored.presentation.ui.home.tab.account.ridelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.autored.domain.model.response.RideResponse
import com.ponkratov.autored.domain.usecase.GetJwtResponseUseCase
import com.ponkratov.autored.domain.usecase.GetRideResponsesByAdvertisementIdUseCase
import com.ponkratov.autored.domain.usecase.GetRideResponsesByLessorIdUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class RideListLesseeViewModel(
    private val getRideResponsesByAdvertisementIdUseCase: GetRideResponsesByAdvertisementIdUseCase
) : ViewModel() {

    private var initFlow = MutableSharedFlow<Long>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val loadingFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val errorFlow = MutableSharedFlow<Throwable>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow = MutableSharedFlow<List<RideResponse>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val getDataFlow = initFlow
        .onEach {
            loadingFlow.tryEmit(Unit)
        }
        .onEach { advertisementId ->
            getRideResponsesByAdvertisementIdUseCase(advertisementId)
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

    fun onRefreshSwiped(advertisementId: Long) {
        initFlow.tryEmit(advertisementId)
    }
}