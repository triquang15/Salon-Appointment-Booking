package com.triquang.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.triquang.domain.PaymentMethod;
import com.triquang.domain.PaymentOrderStatus;
import com.triquang.message.BookingEventProducer;
import com.triquang.message.NotificationEventProducer;
import com.triquang.modal.PaymentOrder;
import com.triquang.payload.BookingDto;
import com.triquang.payload.PayPalLinkResponse;
import com.triquang.payload.PaymentLinkResponse;
import com.triquang.payload.UserDto;
import com.triquang.repository.PaymentOrderRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentOrderRepository paymentRepository;
	
	@Autowired
	private APIContext apiContext;

	@Value("${stripe.api.key}")
	private String stripeApiKey;

	@Value("${razorpay.api.key}")
	private String razorApiKey;

	@Value("${razorpay.secret.key}")
	private String razorSecretKey;
	
	@Autowired
	private BookingEventProducer bookingEventProducer;
	
	@Autowired
	private NotificationEventProducer notificationEventProducer;

	@Override
	public PaymentLinkResponse createOrder(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethod) {

		if (bookingDto == null || userDto == null)
			throw new IllegalArgumentException("Invalid booking or user");

		Long amount = (long) Math.round(bookingDto.getTotalPrice() * 100);

		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setAmount(amount);
		paymentOrder.setPaymentMethod(paymentMethod);
		paymentOrder.setBookingId(bookingDto.getId());
		paymentOrder.setSalonId(bookingDto.getSalonId());
		paymentOrder.setUserId(userDto.getId()); 
		paymentOrder.setStatus(PaymentOrderStatus.PENDING);

		PaymentOrder saved = paymentRepository.save(paymentOrder);

		PaymentLinkResponse response = new PaymentLinkResponse();

		if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
			try {
				PaymentLink link = createrRazorPayPaymentLink(userDto, amount, saved.getId());
				response.setPaymentLinkId(link.get("id"));
				response.setPaymentLinkUrl(link.get("short_url"));

				saved.setPaymentLinkId(link.get("id"));
				paymentRepository.save(saved);
			} catch (Exception e) {
				saved.setStatus(PaymentOrderStatus.FAILED);
				paymentRepository.save(saved);
				throw new RuntimeException("Failed to create RazorPay link", e);
			}
			return response;
		}

		else if (paymentMethod.equals(PaymentMethod.STRIPE)) {
			try {
				String url = createStripePaymentLink(userDto, amount, saved.getId());
				response.setPaymentLinkUrl(url);
			} catch (Exception e) {
				saved.setStatus(PaymentOrderStatus.FAILED);
				paymentRepository.save(saved);
				throw new RuntimeException("Failed to create Stripe link", e);
			}
			return response;
		}
		
		else if (paymentMethod.equals(PaymentMethod.PAYPAL)) {
		    try {
				PayPalLinkResponse paypal = createPayPalPaymentLink(userDto, amount, saved.getId());

				response.setPaymentLinkUrl(paypal.getApproveLink());

				saved.setPaymentLinkId(paypal.getToken());
				paymentRepository.save(saved);

		    } catch (Exception e) {
		        saved.setStatus(PaymentOrderStatus.FAILED);
		        paymentRepository.save(saved);
		        throw new RuntimeException("Failed to create PayPal link", e);
		    }
		    return response;
		}

		throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
	}

	@Override
	public PaymentOrder getPaymentOrderById(Long id) throws Exception {
		PaymentOrder order = paymentRepository.findById(id).orElse(null);
		if (order == null) {
			throw new Exception("Payment order not found with id: " + id);
		}
		return order;
	}

	@Override
	public PaymentOrder getOrderByPaymentLinkId(String paymentLinkId) {
		return paymentRepository.findByPaymentLinkId(paymentLinkId);
	}

	@Override
	public PaymentLink createrRazorPayPaymentLink(UserDto userDto, Long amount, Long orderId) {
		Long amountPayment = (long) Math.round(amount * 100);
		try {
			RazorpayClient client = new RazorpayClient(razorApiKey, razorSecretKey);

			JSONObject request = new JSONObject();
			request.put("amount", amountPayment);
			request.put("currency", "INR");
			request.put("description", "Payment for Order ID: " + orderId);

			JSONObject customer = new JSONObject();
			customer.put("name", userDto.getFullName());
			customer.put("email", userDto.getEmail());
			request.put("customer", customer);

			request.put("notify", new JSONObject().put("sms", true).put("email", true));
			request.put("reminder_enable", true);
			request.put("callback_url", "http://localhost:3000/payment-sucess/" + orderId);	
			request.put("callback_method", "get");

			PaymentLink link = client.paymentLink.create(request);
			return link;
		} catch (Exception e) {
			throw new RuntimeException("Error creating RazorPay payment link", e);
		}
	}

	@Override
	public String createStripePaymentLink(UserDto userDto, Long amountInCents, Long orderId) {
	    Stripe.apiKey = stripeApiKey;

	    try {
	        SessionCreateParams params =
	                SessionCreateParams.builder()
	                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
	                        .setMode(SessionCreateParams.Mode.PAYMENT)
	                        .setSuccessUrl("http://localhost:3000/payment-sucess/" + orderId)
	                        .setCancelUrl("http://localhost:3000/payment-failed/" + orderId)
	                        .addLineItem(
	                                SessionCreateParams.LineItem.builder()
	                                        .setQuantity(1L)
	                                        .setPriceData(
	                                                SessionCreateParams.LineItem.PriceData.builder()
	                                                        .setCurrency("usd")
	                                                        .setUnitAmount(amountInCents)
	                                                        .setProductData(
	                                                                SessionCreateParams.LineItem.PriceData.ProductData
	                                                                        .builder()
	                                                                        .setName("Payment for Order ID: " + orderId)
	                                                                        .build())
	                                                        .build())
	                                        .build())
	                        .putMetadata("orderId", orderId.toString())
	                        .putMetadata("userEmail", userDto.getEmail())
	                        .build();

	        Session session = Session.create(params);
	        return session.getUrl();

	    } catch (Exception e) {
	        throw new RuntimeException("Error creating Stripe Checkout Session", e);
	    }
	}

	@Override
	public PayPalLinkResponse createPayPalPaymentLink(
	        UserDto userDto,
	        Long amountInCents,
	        Long orderId
	) {
	    try {
	        double amount = amountInCents / 100.0;

	        Amount paypalAmount = new Amount();
	        paypalAmount.setCurrency("USD");
	        paypalAmount.setTotal(String.format("%.2f", amount));

	        Transaction transaction = new Transaction();
	        transaction.setDescription("Payment for Order ID: " + orderId);
	        transaction.setAmount(paypalAmount);

	        Payer payer = new Payer();
	        payer.setPaymentMethod("paypal");

	        Payment payment = new Payment();
	        payment.setIntent("sale");
	        payment.setPayer(payer);
	        payment.setTransactions(List.of(transaction));

	        RedirectUrls redirectUrls = new RedirectUrls();
	        redirectUrls.setCancelUrl("http://localhost:3000/payment-failed/" + orderId);
	        redirectUrls.setReturnUrl("http://localhost:3000/payment-success/" + orderId);
	        payment.setRedirectUrls(redirectUrls);

	        Payment createdPayment = payment.create(apiContext);

	        String approveUrl = null;
	        String token = null;

	        for (Links link : createdPayment.getLinks()) {
	            if ("approval_url".equalsIgnoreCase(link.getRel())) {
	                approveUrl = link.getHref();
	                token = link.getHref()
	                        .substring(link.getHref().indexOf("token=") + 6);
	                break;
	            }
	        }

	        if (approveUrl == null || token == null) {
	            throw new RuntimeException("PayPal approval link not found");
	        }

	        return new PayPalLinkResponse(approveUrl, token);

	    } catch (Exception e) {
	        throw new RuntimeException("Error creating PayPal payment", e);
	    }
	}

	@Override
	public Boolean processPayment(PaymentOrder order, String paymentId, String paymentLinkId) {

		if (order.getStatus() != PaymentOrderStatus.PENDING) {
			return false;
		}

		try {
			boolean success = false;

			// -------------------------//
			// 1. Razorpay
			// -------------------------//
			if (order.getPaymentMethod() == PaymentMethod.RAZORPAY) {
				RazorpayClient client = new RazorpayClient(razorApiKey, razorSecretKey);
				com.razorpay.Payment payment = client.payments.fetch(paymentId);
				success = "captured".equals(payment.get("status"));
			}

			// -------------------------//
			// 2. Stripe
			// -------------------------//
			else if (order.getPaymentMethod() == PaymentMethod.STRIPE) {
				Stripe.apiKey = stripeApiKey;
				Session session = Session.retrieve(paymentId);
				success = "paid".equals(session.getPaymentStatus());
			}

			// -------------------------//
			// 3. PayPal
			// -------------------------//
			else if (order.getPaymentMethod() == PaymentMethod.PAYPAL) {
				Payment payment = Payment.get(apiContext, paymentId);
				success = "approved".equalsIgnoreCase(payment.getState());
			}

			// -------------------------//
			// UPDATE DB
			// -------------------------//
			order.setStatus(success ? PaymentOrderStatus.COMPLETED : PaymentOrderStatus.FAILED);
			paymentRepository.save(order);

			//  RABBITMQ EVENTS 

			// 1️ Notify Booking Service
			bookingEventProducer.publish(order);

			// 2️ Notify Notification Service
			notificationEventProducer.publish(
					order.getBookingId(),
					order.getUserId(),
					order.getSalonId()
			);

			return success;

		} catch (Exception e) {

			order.setStatus(PaymentOrderStatus.FAILED);
			paymentRepository.save(order);

			// SEND FAILED EVENTS
			bookingEventProducer.publish(order);

			notificationEventProducer.publish(
					order.getBookingId(),
					order.getUserId(),
					order.getSalonId()
			);

			return false;
		}
	}
}
