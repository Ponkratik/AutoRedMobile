package com.ponkratov.autored.data.repository

import com.ponkratov.autored.data.api.ReviewApi
import com.ponkratov.autored.data.mapper.toData
import com.ponkratov.autored.domain.model.ReviewCar
import com.ponkratov.autored.domain.model.ReviewUser
import com.ponkratov.autored.domain.repository.ReviewRepository

class ReviewRepositoryImpl(
    private val reviewApi: ReviewApi
) : ReviewRepository {
    override suspend fun addCarReview(reviewCar: ReviewCar): Result<String> = runCatching {
        reviewApi.addCarReview(reviewCar.toData()).message
    }

    override suspend fun addUserReview(reviewUser: ReviewUser): Result<String> = runCatching {
        reviewApi.addUserReview(reviewUser.toData()).message
    }

}