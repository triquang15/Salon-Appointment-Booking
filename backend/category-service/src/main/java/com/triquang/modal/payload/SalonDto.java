package com.triquang.modal.payload;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SalonDto {
	private Long id;
	private String name;
	private String email;
	private List<String> images;
	private String address;
	private String phoneNumber;
	private String city;
	private Long ownerId;
	private LocalDateTime openingTime;
	private LocalDateTime closingTime;
}
