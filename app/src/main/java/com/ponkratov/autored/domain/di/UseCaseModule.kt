package com.ponkratov.autored.domain.di

import com.ponkratov.autored.domain.usecase.LoginUseCase
import com.ponkratov.autored.domain.usecase.RegisterUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::LoginUseCase)
    singleOf(::RegisterUseCase)
}