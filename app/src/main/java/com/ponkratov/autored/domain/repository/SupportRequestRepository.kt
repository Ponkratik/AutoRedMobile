package com.ponkratov.autored.domain.repository

import com.ponkratov.autored.domain.model.SupportRequest

interface SupportRequestRepository {

    suspend fun createSupportRequest(supportRequest: SupportRequest): Result<String>
}