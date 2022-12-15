package com.ponkratov.autored.presentation.ui.home.tab.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.autored.domain.model.response.AdvertisementResponse
import com.ponkratov.autored.domain.usecase.GetAdvertisementResponsesByUserIdUseCase
import com.ponkratov.autored.domain.usecase.GetJwtResponseUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class AccountViewModel(
    private val getAdvertisementResponsesByUserIdUseCase: GetAdvertisementResponsesByUserIdUseCase,
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

    val dataFlow = MutableSharedFlow<List<AdvertisementResponse>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val getDataFlow = initFlow
        .onEach {
            loadingFlow.tryEmit(Unit)
        }
        .onEach {
            getAdvertisementResponsesByUserIdUseCase(getJwtResponseUseCase().id)
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

    init {
        initFlow.tryEmit(Unit)
    }
}