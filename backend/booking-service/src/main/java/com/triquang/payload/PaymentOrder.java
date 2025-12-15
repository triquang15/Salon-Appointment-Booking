package com.triquang.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrder {

	private Long id;

	private Long amount;

	private PaymentMethod paymentMethod;

	private String paymentLinkId;

	private Long userId;

	private Long bookingId;
	
	private Long salonId;
}
