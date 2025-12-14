package com.triquang.payload;

import lombok.Data;

@Data
public class ReviewRequest {
	private String reviewText;
	private double rating;
}
