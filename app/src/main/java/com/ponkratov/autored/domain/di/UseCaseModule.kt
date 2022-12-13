package com.ponkratov.autored.domain.di

import com.ponkratov.autored.domain.usecase.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::LoginUseCase)
    singleOf(::RegisterUseCase)
    singleOf(::SaveJwtResponseUseCase)
    singleOf(::GetJwtResponseUseCase)
    singleOf(::GetAdvertisementResponseUseCase)
    singleOf(::GetAdvertisementResponsesUseCase)
    singleOf(::AddAdvertisementUseCase)
}