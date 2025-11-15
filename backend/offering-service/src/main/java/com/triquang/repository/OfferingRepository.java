package com.triquang.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triquang.modal.Offering;

public interface OfferingRepository extends JpaRepository<Offering, Long> {
	Set<Offering> findBySalonId(Long salonId);
}
