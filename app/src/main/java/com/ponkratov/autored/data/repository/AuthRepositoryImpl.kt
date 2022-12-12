package com.ponkratov.autored.data.repository

import com.ponkratov.autored.data.api.AuthApi
import com.ponkratov.autored.data.mapper.toData
import com.ponkratov.autored.data.mapper.toDomain
import com.ponkratov.autored.domain.model.request.LoginRequest
import com.ponkratov.autored.domain.model.request.RegisterRequest
import com.ponkratov.autored.domain.model.response.JwtResponse
import com.ponkratov.autored.domain.repository.AuthRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<JwtResponse> = runCatching {
        authApi.login(
            loginRequest.toData()
        ).toDomain()
    }

    override suspend fun register(
        registerRequest: RegisterRequest,
        profilePhoto: File,
        passportPhoto: File,
        driverLicensePhoto: File
    ): Result<String> = runCatching {
        val profileBody =
            MultipartBody.Part.createFormData(
                "avatar",
                profilePhoto.name,
                profilePhoto.asRequestBody("image/*".toMediaTypeOrNull())
            )
        val passportBody =
            MultipartBody.Part.createFormData(
                "passportPhoto",
                passportPhoto.name,
                passportPhoto.asRequestBody("image/*".toMediaTypeOrNull())
            )
        val driverLicenseBody =
            MultipartBody.Part.createFormData(
                "driverLicensePhoto",
                driverLicensePhoto.name,
                driverLicensePhoto.asRequestBody("image/*".toMediaTypeOrNull())
            )

        authApi.register(
            registerRequest = registerRequest.toData(),
            avatarPhoto = profileBody,
            passportPhoto = passportBody,
            driverLicensePhoto = driverLicenseBody
        ).message
    }
}