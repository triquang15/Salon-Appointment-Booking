package com.triquang.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.payload.AuthResponse;
import com.triquang.payload.LoginDto;
import com.triquang.payload.SignupDto;
import com.triquang.payload.response.ApiResponseBody;
import com.triquang.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponseBody<AuthResponse>> signupHandler(@RequestBody SignupDto req) throws Exception {
		var response = authService.signup(req);
		return ResponseEntity.ok(new ApiResponseBody<>(true, "User created successfully", response));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponseBody<AuthResponse>> loginHandler(@RequestBody LoginDto req) throws Exception {
		var response = authService.login(req.getEmail(), req.getPassword());
		return ResponseEntity.ok(new ApiResponseBody<>(true, "User logged in successfully", response));
	}

	@GetMapping("/refresh-token/{refreshToken}")
	public ResponseEntity<ApiResponseBody<AuthResponse>> refreshToken(@PathVariable String refreshToken)
			throws Exception {
		var authResponse = authService.refreshToken(refreshToken);
		return ResponseEntity.ok(new ApiResponseBody<>(true, "refresh token received successfully", authResponse));
	}

}
