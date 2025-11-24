package com.triquang.payload;

import lombok.Data;

@Data
public class AuthResponse {
	private String jwtToken;
	private String refreshToken;
	private String message;
	private UserRole role;
}
