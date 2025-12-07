package com.triquang.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.triquang.modal.payload.SalonDto;

@FeignClient("SALON-SERVICE")
public interface SalonFeignClient {

	@GetMapping("/api/salons/owner")
	public ResponseEntity<List<SalonDto>> getSalonsByOwnerId(@RequestHeader("Authorization") String token) throws Exception;
}
