package com.ponkratov.autored.presentation.ui.home.tab.history.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.autored.domain.model.ReviewCar
import com.ponkratov.autored.domain.model.ReviewUser
import com.ponkratov.autored.domain.usecase.AddReviewCarUseCase
import com.ponkratov.autored.domain.usecase.AddReviewUserUseCase
import com.ponkratov.autored.domain.usecase.GetJwtResponseUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class AddReviewLessorViewModel(
    private val addReviewUserUseCase: AddReviewUserUseCase,
    private val addReviewCarUseCase: AddReviewCarUseCase,
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
            addReviewUserUseCase(requireNotNull(reviewUser))
                .fold(
                    onSuccess = {
                        addReviewCarUseCase(requireNotNull(reviewCar))
                            .fold(
                                onSuccess = {
                                    dataFlow.tryEmit(it)
                                },
                                onFailure = {
                                    errorFlow.tryEmit(it)
                                }
                            )
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

    private var reviewUser: ReviewUser? = null
    private var reviewCar: ReviewCar? = null

    fun onSendButtonClicked(markCar: Int, commentCar: String, carTo: Long, markUser: Int, commentUser: String, userTo: Long) {
        reviewUser = ReviewUser(
            mark = markUser,
            comment = commentUser,
            userFrom = getJwtResponseUseCase().id,
            userTo = userTo
        )

        reviewCar = ReviewCar(
            mark = markCar,
            comment = commentUser,
            userFrom = getJwtResponseUseCase().id,
            carTo = carTo
        )

        initFlow.tryEmit(Unit)
    }
}