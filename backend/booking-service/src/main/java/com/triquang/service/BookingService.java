package com.triquang.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.triquang.domain.BookingStatus;
import com.triquang.domain.SalonReport;
import com.triquang.modal.Booking;
import com.triquang.payload.BookingRequest;
import com.triquang.payload.OfferingDto;
import com.triquang.payload.SalonDto;
import com.triquang.payload.UserDto;

public interface BookingService {
	Booking createBooking(BookingRequest bookingRequest, UserDto userDto, SalonDto salonDto,
			Set<OfferingDto> offeringDtos);

	List<Booking> getBookingsByCustomer(Long customerId);

	List<Booking> getBookingsBySalon(Long salonId);

	Booking getBookingById(Long bookingId) throws Exception;

	Booking updateBookingStatus(Long bookingId, BookingStatus status) throws Exception;

	List<Booking> getBookingByDate(LocalDateTime date, Long salonId);

	SalonReport getSalonReport(Long salonId);
}
