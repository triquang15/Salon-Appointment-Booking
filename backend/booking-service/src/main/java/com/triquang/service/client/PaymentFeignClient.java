package com.triquang.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.triquang.payload.BookingDto;
import com.triquang.payload.PaymentMethod;

@FeignClient("PAYMENT-SERVICE")
public interface PaymentFeignClient {

	@PostMapping("/api/payments/create")
	public ResponseEntity<?> createPayment(@RequestBody BookingDto bookingDto, @RequestParam PaymentMethod paymentMethod, @RequestHeader("Authorization") String token);
}
