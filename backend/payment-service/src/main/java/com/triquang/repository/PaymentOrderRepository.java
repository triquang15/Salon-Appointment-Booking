package com.triquang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triquang.modal.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
	PaymentOrder findByPaymentLinkId(String paymentLinkId);
}
