package com.ponkratov.autored.presentation.ui.home.tab.search.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.autored.domain.usecase.BookRideUseCase
import com.ponkratov.autored.domain.usecase.GetJwtResponseUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class AdvertisementDetailsViewModel(
    private val bookRideUseCase: BookRideUseCase,
    private val getJwtResponseUseCase: GetJwtResponseUseCase
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

    val dataFlow = MutableSharedFlow<String>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val bookingFlow = initFlow
        .onEach {
            loadingFlow.tryEmit(Unit)
        }
        .onEach { advId ->
            bookRideUseCase(advId, getJwtResponseUseCase().id)
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


    fun onBookButtonClicked(advertisementId: Long) {
        initFlow.tryEmit(advertisementId)
    }
}