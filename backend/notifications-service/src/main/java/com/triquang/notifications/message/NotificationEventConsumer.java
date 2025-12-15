package com.triquang.notifications.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import com.triquang.notifications.config.RabbitMQConfig;
import com.triquang.notifications.modal.Notification;
import com.triquang.notifications.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class NotificationEventConsumer {
	private final NotificationService notificationService;
	
	@RabbitListener(queues = RabbitMQConfig.NOTIFICATIONS_QUEUE)
	public void sendNotificationEvent(Notification notification) throws Exception {
		notificationService.createNotification(notification);
	}
}
