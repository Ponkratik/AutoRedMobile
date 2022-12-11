package com.ponkratov.autored.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ponkratov.autored.data.sharedprefs.JwtSharedPrefs
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val sharedPrefsModule = module {
    single {
        provideSharedPref(get())
    }

    singleOf(::JwtSharedPrefs)
}

fun provideSharedPref(app: Application): SharedPreferences {
    return app.applicationContext.getSharedPreferences(
        SHARED_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )
}

private const val SHARED_PREFERENCE_NAME = "autored_mobile_shared_prefs"