package com.triquang.payload;

import com.triquang.domain.UserRole;

import lombok.Data;

@Data
public class SignupDto {
	private String fullName;	
	private String email;
	private String password;
	private String username;
	private UserRole role;
}
