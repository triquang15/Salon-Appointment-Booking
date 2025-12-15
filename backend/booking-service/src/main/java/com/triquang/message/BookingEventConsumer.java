package com.triquang.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import com.triquang.config.RabbitMQConfig;
import com.triquang.payload.PaymentOrder;
import com.triquang.service.BookingService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BookingEventConsumer {
	private final BookingService bookingService;

	@RabbitListener(queues = RabbitMQConfig.BOOKING_QUEUE)
	public void bookingUpdateListener(PaymentOrder paymentOrder) throws Exception {
		bookingService.bookingSuccess(paymentOrder);
	}
}
