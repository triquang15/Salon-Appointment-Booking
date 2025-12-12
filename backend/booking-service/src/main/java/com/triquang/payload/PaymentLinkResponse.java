package com.triquang.payload;

import lombok.Data;

@Data
public class PaymentLinkResponse {
	private String paymentLinkUrl;
	private String paymentLinkId;
}
