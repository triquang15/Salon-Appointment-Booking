package com.triquang.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.triquang.modal.User;
import com.triquang.payload.AuthResponse;
import com.triquang.payload.SignupDto;
import com.triquang.repository.UserRepository;
import com.triquang.service.AuthService;
import com.triquang.service.KeyCloakService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	private final KeyCloakService keyCloakService;

	@Override
	public AuthResponse login(String username, String password) throws Exception {
		var tokenResponse = keyCloakService.getAdminAccessToken(username, password, "password", null);

		var authResponse = new AuthResponse();
		authResponse.setJwtToken(tokenResponse.getAccessToken());
		authResponse.setRefreshToken(tokenResponse.getRefreshToken());
		authResponse.setMessage("User logged in successfully");

		return authResponse;
	}

	@Override
	public AuthResponse refreshToken(String refreshToken) throws Exception {
		var tokenResponse = keyCloakService.getAdminAccessToken(null, null, "refresh_token", refreshToken);

		var authResponse = new AuthResponse();
		authResponse.setJwtToken(tokenResponse.getAccessToken());
		authResponse.setRefreshToken(tokenResponse.getRefreshToken());
		authResponse.setMessage("Token refreshed successfully");

		return authResponse;
	}

	@Override
	public AuthResponse signup(SignupDto signupDto) throws Exception {
		keyCloakService.createUser(signupDto);

		User user = new User();
		user.setUsername(signupDto.getUsername());
		user.setPassword(signupDto.getPassword());
		user.setEmail(signupDto.getEmail());
		user.setRole(signupDto.getRole());
		user.setFullName(signupDto.getFullName());
		user.setCreateAt(LocalDateTime.now());

		userRepository.save(user);

		var tokenResponse = keyCloakService.getAdminAccessToken(signupDto.getUsername(), signupDto.getPassword(), "password", null);

		var authResponse = new AuthResponse();
		authResponse.setJwtToken(tokenResponse.getAccessToken());
		authResponse.setRefreshToken(tokenResponse.getRefreshToken());
		authResponse.setMessage("User registered successfully");
		authResponse.setRole(signupDto.getRole());

		return authResponse;
	}

}
