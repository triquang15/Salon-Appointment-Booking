package com.triquang.modal;

import com.triquang.domain.PaymentMethod;
import com.triquang.domain.PaymentOrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long amount;

	@Column(nullable = false)
	private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

	@Column(nullable = false)
	private PaymentMethod paymentMethod;

	private String paymentLinkId;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private Long bookingId;

	@Column(nullable = false)
	private Long salonId;
}
