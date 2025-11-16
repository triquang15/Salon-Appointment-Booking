package com.triquang.modal;

import java.time.LocalDateTime;
import java.util.Set;

import com.triquang.domain.BookingStatus;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long salonId;
	private Long customerId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@ElementCollection
	private Set<Long> offeringIds;

	private BookingStatus status = BookingStatus.PENDING;
	private int totalPrice;
}
