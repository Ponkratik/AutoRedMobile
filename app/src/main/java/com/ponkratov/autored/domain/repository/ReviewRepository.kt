package com.ponkratov.autored.domain.repository

import com.ponkratov.autored.domain.model.ReviewCar
import com.ponkratov.autored.domain.model.ReviewUser

interface ReviewRepository {

    suspend fun addCarReview(reviewCar: ReviewCar): Result<String>

    suspend fun addUserReview(reviewUser: ReviewUser): Result<String>
}