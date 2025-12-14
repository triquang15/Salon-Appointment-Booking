package com.triquang.notifications.payload;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
	private String fullName;
	private String email;
}
