package com.ponkratov.autored.presentation.di

import com.ponkratov.autored.presentation.ui.home.tab.search.list.SearchViewModel
import com.ponkratov.autored.presentation.ui.login.LoginViewModel
import com.ponkratov.autored.presentation.ui.registration.RegisterPhotoViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterPhotoViewModel)
    viewModelOf(::SearchViewModel)
}