package com.ponkratov.autored.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ponkratov.autored.domain.model.request.LoginRequest
import com.ponkratov.autored.domain.usecase.LoginUseCase
import com.ponkratov.autored.domain.usecase.SaveJwtResponseUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import retrofit2.HttpException

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val saveJwtResponseUseCase: SaveJwtResponseUseCase
) : ViewModel() {
    private var authFlow = MutableSharedFlow<LoginRequest>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val loadingFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val errorFlow = MutableSharedFlow<String>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val loginFlow = authFlow
        .onEach {
            loadingFlow.tryEmit(Unit)
        }
        .onEach { loginRequest ->
            loginUseCase(loginRequest)
                .fold(
                    onSuccess = {
                        saveJwtResponseUseCase(it)
                        dataFlow.tryEmit(Unit)
                    },
                    onFailure = {
                        errorFlow.tryEmit(
                            when ((it as HttpException).code()) {
                                401 -> "Неправильный логин или пароль/Ваш аккаунт заблокирован или не верифицирован"
                                else -> "Непредвиденная ошибка. Повторите позже"

                            }
                        )
                    }
                )
        }.shareIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            replay = 1
        )


    fun onLoginButtonClicked(email: String, password: String) {
        authFlow.tryEmit(LoginRequest(email, password))
    }
}