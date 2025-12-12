package com.triquang.payload;

import com.triquang.modal.Booking;

public class BookingMapper {
	public static BookingDto toDto(Booking booking) {
		BookingDto dto = new BookingDto();
		dto.setId(booking.getId());
		dto.setSalonId(booking.getSalonId());
		dto.setCustomerId(booking.getCustomerId());
		dto.setStartTime(booking.getStartTime());
		dto.setEndTime(booking.getEndTime());
		dto.setOfferingIds(booking.getOfferingIds());
		dto.setStatus(booking.getStatus());
		dto.setTotalPrice(booking.getTotalPrice()); 
		return dto;
	}
}
