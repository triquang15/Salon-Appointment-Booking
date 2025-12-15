package com.triquang.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.triquang.config.RabbitMQConfig;
import com.triquang.modal.PaymentOrder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingEventProducer {
	
	private final RabbitTemplate rabbitTemplate;

	public void publish(PaymentOrder order) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.BOOKING_QUEUE, order);

	}
}
