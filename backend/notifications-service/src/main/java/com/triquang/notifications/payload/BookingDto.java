package com.triquang.notifications.payload;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class BookingDto {
	private Long id;
	private Long salonId;
	private Long customerId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Set<Long> offeringIds;
	private BookingStatus status = BookingStatus.PENDING;
	private int totalPrice;
}
