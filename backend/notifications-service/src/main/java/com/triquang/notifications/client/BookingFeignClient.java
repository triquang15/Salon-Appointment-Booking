package com.triquang.notifications.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.triquang.notifications.payload.BookingDto;

@FeignClient("BOOKING-SERVICE")
public interface BookingFeignClient {
	
	@GetMapping("/api/bookings/{bookingId}")
	public ResponseEntity<BookingDto> getBookingsById(@PathVariable Long bookingId) throws Exception;
}
