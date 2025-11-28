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
import com.triquang.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signUp(@RequestBody SignupDto signupDto) throws Exception {
		var authResponse = authService.signup(signupDto);
		return ResponseEntity.ok(authResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) throws Exception {
		var authResponse = authService.login(loginDto.getEmail(), loginDto.getPassword());
		return ResponseEntity.ok(authResponse);
	}

	@GetMapping("/refresh-token/{refreshToken}")
	public ResponseEntity<AuthResponse> refreshToken(@PathVariable String refreshToken) throws Exception {
		var authResponse = authService.refreshToken(refreshToken);
		return ResponseEntity.ok(authResponse);
	}
}
