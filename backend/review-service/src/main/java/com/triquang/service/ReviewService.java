package com.triquang.service;

import java.util.List;

import com.triquang.modal.Review;
import com.triquang.payload.ReviewRequest;
import com.triquang.payload.SalonDto;
import com.triquang.payload.UserDto;

public interface ReviewService {
	Review createReview(ReviewRequest req, UserDto userDto, SalonDto salonDto);

	List<Review> getReviewBySalonId(Long salonId);

	Review updateReview(ReviewRequest request, Long reviewId, Long userId) throws Exception;

	void deleteReview(Long reviewId, Long userId) throws Exception;
}
