package com.triquang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.triquang.modal.Review;
import com.triquang.payload.ReviewRequest;
import com.triquang.payload.SalonDto;
import com.triquang.payload.UserDto;
import com.triquang.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;

	@Override
	public Review createReview(ReviewRequest req, UserDto userDto, SalonDto salonDto) {
		Review review = new Review();

		review.setReviewText(req.getReviewText());
		review.setRating(req.getRating());
		review.setUserId(userDto.getId());
		review.setSalonId(salonDto.getId());

		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getReviewBySalonId(Long salonId) {
		return reviewRepository.findBySalonId(salonId);
	}

	/**
	 * Update review (only owner can update)
	 * 
	 * @throws Exception
	 */
	@Override
	public Review updateReview(ReviewRequest request, Long reviewId, Long userId) throws Exception {

		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("Review not found"));

		// Authorization check
		if (!review.getUserId().equals(userId)) {
			throw new Exception("You are not allowed to update this review");
		}

		review.setReviewText(request.getReviewText());
		review.setRating(request.getRating());

		return reviewRepository.save(review);
	}

	/**
	 * Delete review (only owner can delete)
	 * 
	 * @throws Exception
	 */
	@Override
	public void deleteReview(Long reviewId, Long userId) throws Exception {

		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("Review not found"));

		// Authorization check
		if (!review.getUserId().equals(userId)) {
			throw new Exception("You are not allowed to delete this review");
		}

		reviewRepository.delete(review);
	}
}
