package com.triquang.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triquang.domain.BookingStatus;
import com.triquang.modal.Booking;
import com.triquang.payload.BookingDto;
import com.triquang.payload.BookingMapper;
import com.triquang.payload.BookingRequest;
import com.triquang.payload.BookingSlotDto;
import com.triquang.payload.PaymentLinkResponse;
import com.triquang.payload.PaymentMethod;
import com.triquang.service.BookingService;
import com.triquang.service.client.OfferingFeignClient;
import com.triquang.service.client.PaymentFeignClient;
import com.triquang.service.client.SalonFeignClient;
import com.triquang.service.client.UserFeignClient;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private SalonFeignClient salonFeignClient;
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@Autowired
	private OfferingFeignClient offeringFeignClient;
	
	@Autowired
	private PaymentFeignClient paymentFeignClient;
	
	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping
	public ResponseEntity<PaymentLinkResponse> createBooking(
	        @RequestParam Long salonId,
	        @RequestParam PaymentMethod paymentMethod,
	        @RequestBody BookingRequest bookingRequest,
	        @RequestHeader("Authorization") String token) throws Exception {

	    var userDto = userFeignClient.getUserInfo(token).getBody();
	    var salonDto = salonFeignClient.getSalonById(salonId).getBody();
	    var offeringDtos = offeringFeignClient.getServiceOfferingByIds(
	            bookingRequest.getOfferingIds()).getBody();
	    
	    if(offeringDtos.isEmpty()) {
	    	throw new Exception("Service not found...");
	    }

	    var booking = bookingService.createBooking(bookingRequest, userDto, salonDto, offeringDtos);

	    if (booking == null) {
	        return ResponseEntity.badRequest().build();
	    }

	    BookingDto bookingDto = BookingMapper.toDto(booking);

	    // Feign returns ResponseEntity<?> → we extract the raw Object
	    Object body = paymentFeignClient.createPayment(bookingDto, paymentMethod, token).getBody();

	    // Convert Object → PaymentLinkResponse
	    PaymentLinkResponse paymentResponse =
	            objectMapper.convertValue(body, PaymentLinkResponse.class);

	    return ResponseEntity.ok(paymentResponse);
	}


	@GetMapping("/customer")
	public ResponseEntity<Set<BookingDto>> getBookingsByCustomer(@RequestHeader("Authorization") String token) throws Exception {
		var userDto = userFeignClient.getUserInfo(token).getBody();
		if(userDto == null || userDto.getId() == null) {
			throw new Exception("User not found with token...!");
		}
		List<Booking> bookings = bookingService.getBookingsByCustomer(userDto.getId());
		return ResponseEntity.ok(getBookings(bookings));
	}

	@GetMapping("/salon")
	public ResponseEntity<Set<BookingDto>> getBookingsBySalon(@RequestHeader("Authorization") String token) throws Exception {
		var salonDto = salonFeignClient.getSalonsByOwnerId(token).getBody();
		var bookings = bookingService.getBookingsBySalon(salonDto.get(0).getId());
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
	public ResponseEntity<?> getSalonReport(@RequestHeader("Authorization") String token) throws Exception {
		var salonDto = salonFeignClient.getSalonsByOwnerId(token).getBody();
		var report = bookingService.getSalonReport(salonDto.get(0).getId());
		return ResponseEntity.ok(report);
	}
}
