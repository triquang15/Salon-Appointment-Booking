package com.triquang.modal;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String reviewText;

	@Column(nullable = false)
	private double rating;

	@Column(nullable = false)
	private Long  salonId;

	@Column(nullable = false)
	private Long  userId;

	@CreatedDate
	private LocalDateTime createdAt;

}
