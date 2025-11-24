package com.triquang.service;

import com.triquang.payload.AuthResponse;
import com.triquang.payload.SignupDto;

public interface AuthService {
	AuthResponse login(String username, String password) throws Exception;
	AuthResponse refreshToken(String refreshToken) throws Exception;
	AuthResponse signup(SignupDto signupDto) throws Exception;
}
