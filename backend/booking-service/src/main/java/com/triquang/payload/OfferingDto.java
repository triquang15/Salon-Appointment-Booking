package com.triquang.payload;

import lombok.Data;

@Data
public class OfferingDto {
	private Long id;
	private String name;
	private String description;
	private int price;
	private int duration;
	private Long salonId;
	private Long categoryId;
	private String image;
}
