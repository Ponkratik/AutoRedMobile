package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.model.SupportRequest
import com.ponkratov.autored.domain.repository.SupportRequestRepository

class CreateSupportRequestUseCase(
    private val supportRequestRepository: SupportRequestRepository
) {

    suspend operator fun invoke(supportRequest: SupportRequest) =
        supportRequestRepository.createSupportRequest(supportRequest)
}