package com.ponkratov.autored.presentation.ui.home.tab.account.ridedetails.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.autored.domain.model.ReviewUser
import com.ponkratov.autored.domain.usecase.AddReviewUserUseCase
import com.ponkratov.autored.domain.usecase.GetJwtResponseUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class AddReviewLesseeViewModel(
    private val addReviewUserUseCase: AddReviewUserUseCase,
    private val getJwtResponseUseCase: GetJwtResponseUseCase
) : ViewModel() {

    private var initFlow = MutableSharedFlow<ReviewUser>(
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
        .onEach { reviewUser ->
            addReviewUserUseCase(reviewUser)
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

    fun onSendButtonClicked(markUser: Int, commentUser: String, userTo: Long) {
        initFlow.tryEmit(
            ReviewUser(
                mark = markUser,
                comment = commentUser,
                userFrom = getJwtResponseUseCase().id,
                userTo = userTo
            )
        )
    }
}