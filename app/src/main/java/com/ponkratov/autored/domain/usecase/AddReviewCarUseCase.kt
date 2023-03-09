package com.ponkratov.autored.domain.usecase

import com.ponkratov.autored.domain.model.ReviewCar
import com.ponkratov.autored.domain.repository.ReviewRepository

class AddReviewCarUseCase(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(reviewCar: ReviewCar) = reviewRepository.addCarReview(reviewCar)
}