package com.ponkratov.autored.data.di

import com.ponkratov.autored.data.repository.*
import com.ponkratov.autored.domain.repository.*
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single {
        AuthRepositoryImpl(get())
    } bind AuthRepository::class

    single {
        SharedPrefsRepositoryImpl(get())
    } bind SharedPrefsRepository::class

    single {
        AdvertisementRepositoryImpl(get())
    } bind AdvertisementRepository::class

    single {
        RideRepositoryImpl(get())
    } bind RideRepository::class

    single {
        SupportRequestRepositoryImpl(get())
    } bind SupportRequestRepository::class

    single {
        ReviewRepositoryImpl(get())
    } bind ReviewRepository::class
}