package com.triquang.notifications.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.triquang.notifications.client.BookingFeignClient;
import com.triquang.notifications.mapper.NotificationMapper;
import com.triquang.notifications.modal.Notification;
import com.triquang.notifications.payload.NotificationDto;
import com.triquang.notifications.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;
	private final BookingFeignClient bookingFeignClient;

	@Override
	public NotificationDto createNotification(Notification notification) throws Exception {
		var newNotifi = notificationRepository.save(notification);
		var bookingDto = bookingFeignClient.getBookingsById(newNotifi.getBookingId()).getBody();

		return NotificationMapper.toDto(newNotifi, bookingDto);

	}

	@Override
	public List<Notification> getAllNotificationByUserId(Long userId) {
		return notificationRepository.findByUserId(userId);
	}

	@Override
	public List<Notification> getAllNotificationBySalonId(Long salonId) {
		return notificationRepository.findBySalonId(salonId);
	}

	@Override
	public Notification markNotificationAsRead(Long notificationId) {
	    Notification n = notificationRepository.findById(notificationId)
	        .orElseThrow(() -> new RuntimeException("Notification not found"));
	    n.setIsRead(true);
	    return notificationRepository.save(n);
	}

	@Override
	public void markAllNotificationsAsReadBySalonId(Long salonId) {
	    List<Notification> list = notificationRepository.findBySalonId(salonId);

	    if (list.isEmpty()) {
	        return;
	    }

	    list.forEach(n -> n.setIsRead(true));

	    notificationRepository.saveAll(list);
	}

	@Override
	public void markAllNotificationsAsReadByUserId(Long userId) {
	    List<Notification> list = notificationRepository.findByUserId(userId);

	    if (list.isEmpty()) {
	        return;
	    }

	    list.forEach(n -> n.setIsRead(true));

	    notificationRepository.saveAll(list);
	}

}
