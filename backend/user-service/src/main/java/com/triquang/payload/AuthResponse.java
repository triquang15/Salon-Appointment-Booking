package com.triquang.payload;

import com.triquang.domain.UserRole;

import lombok.Data;

@Data
public class AuthResponse {
	private String jwt;
	private String refreshToken;
	private String title;
	private String message;
	private UserRole role;
}
