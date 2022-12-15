package com.ponkratov.autored.data.mapper

import com.ponkratov.autored.data.model.SupportRequestDTO
import com.ponkratov.autored.domain.model.SupportRequest

fun SupportRequestDTO.toDomain(): SupportRequest {
    return SupportRequest(
        id, userId, message
    )
}

fun SupportRequest.toData(): SupportRequestDTO {
    return SupportRequestDTO(
        id, userId, message
    )
}