package com.triquang.notifications.service;

import java.util.List;

import com.triquang.notifications.modal.Notification;
import com.triquang.notifications.payload.NotificationDto;

public interface NotificationService {
	NotificationDto createNotification(Notification notification) throws Exception;
	List<Notification> getAllNotificationByUserId(Long userId);
	List<Notification> getAllNotificationBySalonId(Long salonId);
	Notification markNotificationAsRead(Long notificationId);
	void markAllNotificationsAsReadBySalonId(Long salonId);
	void markAllNotificationsAsReadByUserId(Long userId);
}
