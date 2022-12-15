package com.ponkratov.autored.data.repository

import com.ponkratov.autored.data.api.SupportRequestApi
import com.ponkratov.autored.data.mapper.toData
import com.ponkratov.autored.domain.model.SupportRequest
import com.ponkratov.autored.domain.repository.SupportRequestRepository

class SupportRequestRepositoryImpl(
    private val supportRequestApi: SupportRequestApi
) : SupportRequestRepository {

    override suspend fun createSupportRequest(supportRequest: SupportRequest): Result<String> = runCatching {
        supportRequestApi.createSupportRequest(supportRequest.toData()).message
    }
}