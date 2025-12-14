package com.triquang.notifications.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triquang.notifications.client.BookingFeignClient;
import com.triquang.notifications.mapper.NotificationMapper;
import com.triquang.notifications.modal.Notification;
import com.triquang.notifications.payload.BookingDto;
import com.triquang.notifications.payload.NotificationDto;
import com.triquang.notifications.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/notifications/salon-owner")
@RequiredArgsConstructor
@Slf4j
public class SalonNotificationController {

	private final NotificationService notificationService;
	private final BookingFeignClient bookingFeignClient;

	/**
	 * Get notifications by salonId
	 */
	@GetMapping("/salon/{salonId}")
	public ResponseEntity<List<NotificationDto>> getBySalonId(@PathVariable Long salonId) {

		List<Notification> notifications = notificationService.getAllNotificationBySalonId(salonId);

		List<NotificationDto> dtos = notifications.stream().map(notification -> {
			BookingDto bookingDto = null;
			try {
				if (notification.getBookingId() != null) {
					bookingDto = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();
				}
			} catch (Exception ex) {
				log.error("Failed to fetch booking for bookingId={}", notification.getBookingId(), ex);
			}

			return NotificationMapper.toDto(notification, bookingDto);
		}).collect(Collectors.toList());

		return ResponseEntity.ok(dtos);
	}
}
