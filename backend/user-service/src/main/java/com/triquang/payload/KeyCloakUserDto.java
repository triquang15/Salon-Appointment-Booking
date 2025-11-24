package com.triquang.payload;

import lombok.Data;

@Data
public class KeyCloakUserDto {
	private String id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
}
