package com.ponkratov.autored.presentation

import android.app.Application
import com.ponkratov.autored.data.di.networkModule
import com.ponkratov.autored.data.di.repositoryModule
import com.ponkratov.autored.data.di.sharedPrefsModule
import com.ponkratov.autored.domain.di.useCaseModule
import com.ponkratov.autored.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AutoRedApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AutoRedApplication)
            modules(
                viewModelModule,
                useCaseModule,
                repositoryModule,
                sharedPrefsModule,
                networkModule
            )
        }
    }
}