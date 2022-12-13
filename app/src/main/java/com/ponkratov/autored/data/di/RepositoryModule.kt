package com.ponkratov.autored.data.di

import com.ponkratov.autored.data.repository.AdvertisementRepositoryImpl
import com.ponkratov.autored.data.repository.AuthRepositoryImpl
import com.ponkratov.autored.data.repository.RideRepositoryImpl
import com.ponkratov.autored.data.repository.SharedPrefsRepositoryImpl
import com.ponkratov.autored.domain.repository.AdvertisementRepository
import com.ponkratov.autored.domain.repository.AuthRepository
import com.ponkratov.autored.domain.repository.RideRepository
import com.ponkratov.autored.domain.repository.SharedPrefsRepository
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
}