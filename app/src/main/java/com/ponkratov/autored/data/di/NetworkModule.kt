package com.ponkratov.autored.data.di

import com.google.gson.GsonBuilder
import com.ponkratov.autored.data.api.*
import com.ponkratov.autored.data.sharedprefs.JwtSharedPrefs
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {
    single(qualifier(NetworkApiQualifier.NO_AUTH)) {
        provideRetrofit(get(qualifier(NetworkApiQualifier.NO_AUTH)))
    }

    single(qualifier(NetworkApiQualifier.AUTH)) {
        provideRetrofit(get(qualifier(NetworkApiQualifier.AUTH)))
    }

    single(qualifier(NetworkApiQualifier.AUTH)) {
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer ${JwtSharedPrefs(get()).getJwt()}"
                    )
                    .build()
                chain.proceed(request)
            }).build()
    }

    single(qualifier(NetworkApiQualifier.NO_AUTH)) {
        OkHttpClient.Builder().build()
    }

    single { get<Retrofit>(qualifier(NetworkApiQualifier.NO_AUTH)).create<AuthApi>() }
    single { get<Retrofit>(qualifier(NetworkApiQualifier.NO_AUTH)).create<AdvertisementApi>() }
    single { get<Retrofit>(qualifier(NetworkApiQualifier.NO_AUTH)).create<RideApi>() }
    single { get<Retrofit>(qualifier(NetworkApiQualifier.NO_AUTH)).create<SupportRequestApi>() }
    single { get<Retrofit>(qualifier(NetworkApiQualifier.NO_AUTH)).create<ReviewApi>() }
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    return Retrofit.Builder()
        .baseUrl(SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}

private const val SERVER_URL = "http://10.0.2.2:8080/api/"