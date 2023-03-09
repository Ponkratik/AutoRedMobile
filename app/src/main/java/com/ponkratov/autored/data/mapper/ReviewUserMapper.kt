package com.ponkratov.autored.data.mapper

import com.ponkratov.autored.data.model.ReviewUserDTO
import com.ponkratov.autored.domain.model.ReviewUser

fun ReviewUser.toData(): ReviewUserDTO {
    return ReviewUserDTO(
        id, mark, comment, userFrom, userTo
    )
}

fun ReviewUserDTO.toDomain(): ReviewUser {
    return ReviewUser(
        id, mark, comment, userFrom, userTo
    )
}