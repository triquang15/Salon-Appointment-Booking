package com.triquang.notifications.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    private final BookingFeignClient bookingFeignClient;

    /**
     * Create new notification
     */
    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(
            @RequestBody Notification notification) throws Exception {

        NotificationDto dto = notificationService.createNotification(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /**
     * Get notifications by userId
     */
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<NotificationDto>> getByUserId(@PathVariable Long userId) {

		List<Notification> notifications = notificationService.getAllNotificationByUserId(userId);

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

    /**
     * Mark one notification as read
     *  
     */
	@PutMapping("/{notificationId}/read")
	public ResponseEntity<NotificationDto> markAsRead(@PathVariable Long notificationId) throws Exception {

		Notification notification = notificationService.markNotificationAsRead(notificationId);
		BookingDto bookingDto = bookingFeignClient.getBookingsById(notification.getBookingId()).getBody();
		return ResponseEntity.ok(NotificationMapper.toDto(notification, bookingDto));
	}

    /**
     * Mark all notifications as read by userId
     */
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllByUserId(
            @PathVariable Long userId) {

        notificationService.markAllNotificationsAsReadByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark all notifications as read by salonId
     */
    @PutMapping("/salon/{salonId}/read-all")
    public ResponseEntity<Void> markAllBySalonId(
            @PathVariable Long salonId) {

        notificationService.markAllNotificationsAsReadBySalonId(salonId);
        return ResponseEntity.noContent().build();
    }
}
