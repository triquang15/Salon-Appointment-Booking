package com.triquang.payload;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class BookingRequest {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Set<Long> offeringIds;
}
