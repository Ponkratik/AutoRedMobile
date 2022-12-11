package com.ponkratov.autored.data.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.ponkratov.autored.domain.model.response.JwtResponse

class JwtSharedPrefs(
    private val sharedPreferences: SharedPreferences
) {
    fun getJwt(): JwtResponse {
        val str = sharedPreferences.getString(ID_TOKEN_CODE, "") ?: ""
        return Gson().fromJson(str, JwtResponse::class.java)
    }

    fun putJwt(jwt: JwtResponse) {
        val str = Gson().toJson(jwt)
        sharedPreferences.edit { putString(ID_TOKEN_CODE, str) }
    }

    companion object {
        private const val ID_TOKEN_CODE: String = "jwt"
    }
}