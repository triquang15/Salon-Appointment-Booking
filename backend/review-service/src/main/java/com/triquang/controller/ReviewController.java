package com.triquang.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.modal.Review;
import com.triquang.payload.ReviewRequest;
import com.triquang.service.ReviewService;
import com.triquang.service.client.SalonFeignClient;
import com.triquang.service.client.UserFeignClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final UserFeignClient userFeignClient;
	private final SalonFeignClient salonFeignClient;

	/**
	 * Create review (userId & salonId usually come from JWT / Feign in real system)
	 * 
	 * @throws Exception
	 */
	@PostMapping("/salon/{salonId}")
	public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request, @PathVariable Long salonId,
			@RequestHeader("Authorization") String token) throws Exception {

		var userDto = userFeignClient.getUserInfo(token).getBody();
		var salonDto = salonFeignClient.getSalonById(salonId).getBody();
		var review = reviewService.createReview(request, userDto, salonDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(review);
	}

	/**
	 * Get reviews by salonId
	 * 
	 * @throws Exception
	 */
	@GetMapping("/salon/{salonId}")
	public ResponseEntity<List<Review>> getReviewsBySalon(@PathVariable Long salonId,
			@RequestHeader("Authorization") String token) throws Exception {

		userFeignClient.getUserInfo(token).getBody();
		var salonDto = salonFeignClient.getSalonById(salonId).getBody();

		return ResponseEntity.ok(reviewService.getReviewBySalonId(salonDto.getId()));
	}

	/**
	 * Update review (only owner)
	 */
	@PutMapping("/{reviewId}")
	public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequest request,
			@RequestHeader("Authorization") String token) throws Exception {

		var userDto = userFeignClient.getUserInfo(token).getBody();
		return ResponseEntity.ok(reviewService.updateReview(request, reviewId, userDto.getId()));
	}

	/**
	 * Delete review (only owner)
	 */
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @RequestHeader("Authorization") String token)
			throws Exception {
		var userDto = userFeignClient.getUserInfo(token).getBody();
		reviewService.deleteReview(reviewId, userDto.getId());
		return ResponseEntity.noContent().build();
	}
}
