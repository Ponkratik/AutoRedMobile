package com.ponkratov.autored.data.mapper

import com.ponkratov.autored.data.model.ReviewCarDTO
import com.ponkratov.autored.domain.model.ReviewCar

fun ReviewCar.toData(): ReviewCarDTO {
    return ReviewCarDTO(
        id, mark, comment, userFrom, carTo
    )
}

fun ReviewCarDTO.toDomain(): ReviewCar {
    return ReviewCar(
        id, mark, comment, userFrom, carTo
    )
}