package com.triquang.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.domain.BookingStatus;
import com.triquang.modal.Booking;
import com.triquang.payload.BookingDto;
import com.triquang.payload.BookingMapper;
import com.triquang.payload.BookingRequest;
import com.triquang.payload.BookingSlotDto;
import com.triquang.payload.OfferingDto;
import com.triquang.payload.SalonDto;
import com.triquang.payload.UserDto;
import com.triquang.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping
	public ResponseEntity<Booking> createBooking(@RequestParam Long salonId,
			@RequestBody BookingRequest bookingRequest) {
		UserDto userDto = new UserDto();
		userDto.setId(1L);

		SalonDto salonDto = new SalonDto();
		salonDto.setId(salonId);
		salonDto.setOpeningTime(LocalTime.of(8, 0));     // 08:00
	    salonDto.setClosingTime(LocalTime.of(20, 0));    // 20:00

		Set<OfferingDto> offeringDtos = new HashSet<>();
		OfferingDto offering = new OfferingDto();
		offering.setId(1L);
		offering.setPrice(100);
		offering.setDuration(60);
		offering.setName("Haircut");
		offeringDtos.add(offering);

		Booking booking = bookingService.createBooking(bookingRequest, userDto, salonDto, offeringDtos);
		if (booking != null) {
			return ResponseEntity.ok(booking);
		} else {
			return ResponseEntity.badRequest().build();
		}

	}

	@GetMapping("/customer")
	public ResponseEntity<Set<BookingDto>> getBookingsByCustomer() {
		UserDto userDto = new UserDto();
		userDto.setId(1L);
		List<Booking> bookings = bookingService.getBookingsByCustomer(userDto.getId());
		return ResponseEntity.ok(getBookings(bookings));
	}

	@GetMapping("/salon")
	public ResponseEntity<Set<BookingDto>> getBookingsBySalon() {
		List<Booking> bookings = bookingService.getBookingsBySalon(1L);
		return ResponseEntity.ok(getBookings(bookings));
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<BookingDto> getBookingsById(@PathVariable Long bookingId) throws Exception {
		return ResponseEntity.ok(BookingMapper.toDto(bookingService.getBookingById(bookingId)));
	}

	@PutMapping("/{bookingId}/status")
	public ResponseEntity<BookingDto> updateBookingStatus(@PathVariable Long bookingId, @RequestParam String status)
			throws Exception {
		return ResponseEntity
				.ok(BookingMapper.toDto(bookingService.updateBookingStatus(bookingId, BookingStatus.valueOf(status))));
	}

	@GetMapping("/slots/salon/{salonId}")
	public ResponseEntity<List<BookingSlotDto>> getBookingsByDate(
	        @PathVariable Long salonId,
	        @RequestParam LocalDate date) {

	    var bookings = bookingService.getBookingByDate(date.atStartOfDay(), salonId);
	    var bookingSlots = bookings.stream().map(b -> {
	        var slot = new BookingSlotDto();
	        slot.setStartTime(b.getStartTime());
	        slot.setEndTime(b.getEndTime());
	        return slot;
	    }).collect(Collectors.toList());

	    return ResponseEntity.ok(bookingSlots);
	}


	private Set<BookingDto> getBookings(List<Booking> bookings) {
		return bookings.stream().map(b -> {
			return BookingMapper.toDto(b);
		}).collect(Collectors.toSet());
	}

	@GetMapping("/report")
	public ResponseEntity<?> getSalonReport() {
		var report = bookingService.getSalonReport(1L);
		return ResponseEntity.ok(report);
	}
}
