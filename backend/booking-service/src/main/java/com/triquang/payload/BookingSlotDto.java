package com.triquang.payload;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingSlotDto {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
