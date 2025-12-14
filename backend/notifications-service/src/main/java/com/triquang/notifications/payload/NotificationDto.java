package com.triquang.notifications.payload;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationDto {
	private Long id;

	private String type;
	private String description;
	private Boolean isRead = false;
	private Long userId;
	private Long salonId;
	private Long bookingId;
	private LocalDateTime createdAt;
	private BookingDto bookingDto;
}
