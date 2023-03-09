package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.model.ReviewUser
import com.ponkratov.autored.domain.repository.ReviewRepository

class AddReviewUserUseCase(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(reviewUser: ReviewUser) = reviewRepository.addUserReview(reviewUser)
}