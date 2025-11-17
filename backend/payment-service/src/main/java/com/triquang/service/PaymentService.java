package com.triquang.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.triquang.domain.PaymentMethod;
import com.triquang.modal.PaymentOrder;
import com.triquang.payload.BookingDto;
import com.triquang.payload.PaymentLinkResponse;
import com.triquang.payload.UserDto;

public interface PaymentService {
	PaymentLinkResponse createOrder(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethod);

	PaymentOrder getPaymentOrderById(Long id) throws Exception;

	PaymentOrder getOrderByPaymentLinkId(String paymentLinkId);

	PaymentLink createrRazorPayPaymentLink(UserDto userDto, Long amount, Long orderId);

	String createStripePaymentLink(UserDto userDto, Long amount, Long orderId);
	
	String createPayPalPaymentLink(UserDto userDto, Long amount, Long orderId);
	
	Boolean processPayment(PaymentOrder order, String paymentId, String paymentLinkId) throws RazorpayException;

}
