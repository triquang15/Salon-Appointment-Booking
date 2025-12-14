package com.triquang.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.triquang.payload.UserDto;

@FeignClient("USER-SERVICE")
public interface UserFeignClient {

	@GetMapping("/api/users/profile")
	public ResponseEntity<UserDto> getUserInfo(@RequestHeader("Authorization") String token) throws Exception;
}
