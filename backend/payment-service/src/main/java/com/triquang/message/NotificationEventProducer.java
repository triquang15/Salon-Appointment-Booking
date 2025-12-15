package com.triquang.message;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.triquang.config.RabbitMQConfig;
import com.triquang.payload.NotificationDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationEventProducer {

	private final RabbitTemplate rabbitTemplate;

	public void publish(Long bookingId, Long userId, Long salonId) {

		NotificationDto dto = new NotificationDto();
		dto.setType("PAYMENT_SUCCESS");
		dto.setDescription("Your payment for booking #" + bookingId + " was successful.");
		dto.setUserId(userId);
		dto.setSalonId(salonId);
		dto.setBookingId(bookingId);
		dto.setIsRead(false);
		dto.setCreatedAt(LocalDateTime.now());

		// Send to Notification Service
		rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATIONS_QUEUE, dto);
	}
}
