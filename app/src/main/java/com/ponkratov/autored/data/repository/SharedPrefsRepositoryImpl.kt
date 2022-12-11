package com.ponkratov.autored.data.repository

import com.ponkratov.autored.data.sharedprefs.JwtSharedPrefs
import com.ponkratov.autored.domain.model.response.JwtResponse
import com.ponkratov.autored.domain.repository.SharedPrefsRepository

class SharedPrefsRepositoryImpl(
    private val jwtSharedPrefs: JwtSharedPrefs
) : SharedPrefsRepository {
    override fun getJwt(): JwtResponse {
        return jwtSharedPrefs.getJwt()
    }

    override fun putJwt(jwtResponse: JwtResponse) {
        jwtSharedPrefs.putJwt(jwtResponse)
    }
}